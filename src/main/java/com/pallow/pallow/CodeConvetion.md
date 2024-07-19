# Code Convention

## Method 작성 방법

* 생성 : create + entityName
* 수정 : update + entityName
* 삭제 : delete + entityName
* 조회 : get + entityName | getAll + entityName

### example

    @PostMapping("/groups")
    public ResponseEntity<CommonResponseDto> createGroups(
    @Valid @RequestBody GroupRequestDto requestDto,
    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        GroupResponseDto responseDto = groupService.createGroup(requestDto, userDetails.getUser());
        return ResponseEntity(responseDto, "그룹 추가 성공");
    }

## 주석 다는 방법
#### 각 메서드의 윗 부분에 Javadoc 사용

### example ( /** 이후 엔터 )

    /**
    * 주석
    */
    @PostMapping("/groups")
    public ResponseEntity<CommonResponseDto> createGroups(
    @Valid @RequestBody GroupRequestDto requestDto,
    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        GroupResponseDto responseDto = groupService.createGroup(requestDto, userDetails.getUser());
        return ResponseEntity(responseDto, "그룹 추가 성공");
    }

### 5분 기록 보드 test pull request
https://www.notion.so/teamsparta/5-eead60febc5b48d78e269efebb3fef50?pvs=25