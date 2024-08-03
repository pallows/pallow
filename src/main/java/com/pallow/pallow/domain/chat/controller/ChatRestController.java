package com.pallow.pallow.domain.chat.controller;

import com.pallow.pallow.domain.chat.dto.ApiResponse;
import com.pallow.pallow.domain.chat.dto.ChatMessageDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomResponseDto;
import com.pallow.pallow.domain.chat.service.ChatService;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatRestController {

    private final ChatService chatService;


    @PostMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("채팅방을 생성합니다. 사용자: {}, 채팅방 이름: {}", userDetails.getNickname(), chatRoomDto.getName());
try {
    ChatRoomDto chatRoom = chatService.createChatRoom(chatRoomDto, userDetails.getNickname());
    log.info("채팅방 생성 완료: {}", chatRoom.getName());
    return ResponseEntity.ok(new ApiResponse(Message.ROOM_CREATE_SUCCESS, chatRoom));
} catch (Exception e) {
    log.error("채팅방 생성 실패, e");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ErrorType.INTERNAL_SERVER_ERROR, null));
    }
}

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ApiResponse> enterChatRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ChatRoomResponseDto response = chatService.enterChatRoom(roomId, userDetails.getNickname());
            return ResponseEntity.ok(new ApiResponse(Message.ROOM_ENTER_SUCCESS, response));
        } catch (Exception e) {
            throw new CustomException(ErrorType.NOT_FOUND_CHATROOM);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody ChatMessageDto messageDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(ErrorType.NOT_FOUND_USER, null));
        }
        try {
            log.info("Attempting to send message. User: {}, ChatRoom: {}", userDetails.getNickname(), messageDto.getChatRoomId());
            ChatMessageDto sendMessage = chatService.sendAndSaveMessage(messageDto, userDetails.getNickname());
            log.info("Message sent successfully. MessageId: {}", sendMessage.getId());
            return ResponseEntity.ok(new ApiResponse(Message.MESSAGE_CREATE_SUCCESS, sendMessage));
        } catch (CustomException e) {
            log.error("User not found: {}", userDetails.getNickname(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ErrorType.NOT_FOUND_USER, null));
        } catch (Exception e) {
            log.error("Error sending message", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ErrorType.INTERNAL_SERVER_ERROR, null));
        }
    }

    @GetMapping("/rooms")
    public ResponseEntity<ApiResponse> getChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<ChatRoomDto> chatRooms = chatService.getChatRoomsForUser(
                    userDetails.getNickname());
            return ResponseEntity.ok(new ApiResponse(Message.ROOM_READ_SUCCESS, chatRooms));
        } catch (Exception e) {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
    }

    @PostMapping("/Messages/{MessageId}/react/{reactId}")
    public  ResponseEntity<ApiResponse> toggleChatRoom(@PathVariable Long MessageId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ChatMessageDto updatedMessage = chatService.toggleChatReaction(MessageId,
                    userDetails.getUser().getId());
            return ResponseEntity.ok(new ApiResponse(Message.LIKES_TOGGLE_SUCCESS, updatedMessage));
        } catch (Exception e) {
            throw new CustomException(ErrorType.NOT_FOUND_MESSAGE);
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new ApiResponse(ex.getErrorType(), null), ex.getErrorType()
                .getStatus());
    }




}
