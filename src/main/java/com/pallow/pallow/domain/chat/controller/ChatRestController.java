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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final SimpMessagingTemplate messagingTemplate;


    @PostMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ChatRoomDto chatRoom = chatService.createChatRoom(
                    chatRoomDto.getName(),
                    userDetails.getNickname(),
                    chatRoomDto.getOtherUserNickname()
            );
            return ResponseEntity.ok(new ApiResponse(Message.ROOM_CREATE_SUCCESS, chatRoom));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(ErrorType.NOT_FOUND_USER, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ErrorType.INTERNAL_SERVER_ERROR, null));
        }
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ApiResponse> enterChatRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("userDetails 갯 매핑 룸 룸아이디 {} : ", userDetails.getNickname());
        log.info("userDetails 갯 매핑 룸 룸아이디 ID {} : ", roomId);
        try {
            ChatRoomResponseDto response = chatService.enterChatRoom(roomId, userDetails.getNickname());
            log.info("채팅룸 response {} : ", response);

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
            messageDto.setSender(userDetails.getNickname());
            ChatMessageDto sendMessage = chatService.sendAndSaveMessage(messageDto, userDetails.getNickname());
            messagingTemplate.convertAndSend("/topic/chat/" + messageDto.getChatRoomId(), sendMessage);


            return ResponseEntity.ok(new ApiResponse(Message.MESSAGE_CREATE_SUCCESS, sendMessage));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ErrorType.NOT_FOUND_USER, null));
        } catch (Exception e) {
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
    public ResponseEntity<ApiResponse> toggleChatRoom(@PathVariable Long MessageId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ChatMessageDto updatedMessage = chatService.toggleChatReaction(MessageId,
                    userDetails.getUser().getId());
            return ResponseEntity.ok(new ApiResponse(Message.LIKES_TOGGLE_SUCCESS, updatedMessage));
        } catch (Exception e) {
            throw new CustomException(ErrorType.NOT_FOUND_MESSAGE);
        }
    }


    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<ApiResponse> deleteChatRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            chatService.deleteChatRoom(roomId, userDetails.getNickname());
            return ResponseEntity.ok(new ApiResponse(Message.ROOM_DELETE_SUCCESS, null));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorType().getStatus())
                    .body(new ApiResponse(e.getErrorType(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ErrorType.INTERNAL_SERVER_ERROR, null));
        }
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new ApiResponse(ex.getErrorType(), null), ex.getErrorType()
                .getStatus());
    }
}






