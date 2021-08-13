package com.d203.backend.api.controller;

import com.d203.backend.api.request.DirectMessage.ChatRoomPostReq;
import com.d203.backend.api.request.DirectMessage.DirectMessagePostReq;
import com.d203.backend.api.response.DirectMessage.ChatMessageListRes;
import com.d203.backend.api.response.DirectMessage.ChatRoomListRes;
import com.d203.backend.api.service.DirectMessage.DirectMessageService;
import com.d203.backend.common.model.response.BaseResponseBody;
import com.d203.backend.db.entity.ChatJoinInfo;
import com.d203.backend.db.entity.ChatMessage;
import com.d203.backend.db.entity.ChatRoom;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "DirectMessage API", tags = {"DirectMessage"})
@RestController
@RequestMapping("/api/v1/directMessage")
public class DirectMessageController {

    @Autowired
    DirectMessageService directMessageService;

    @PostMapping("/createChatRoom")
    @ApiOperation(value = "채팅방 생성 ", notes = "유저와 상대 유저 사이의 채팅방을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createChatRoom(
            @RequestBody @ApiParam(value = "유저, 상대 유저 ID", required = true) ChatRoomPostReq chatRoomPostReq) {
        directMessageService.createChatRoom(chatRoomPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/sendDirectMessage")
    @ApiOperation(value = "채팅 메세지 저장", notes = "유저 아이디와 방 정보로 메세지를 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> sendDirectMessage(
            @RequestBody @ApiParam(value = "방 ID, 유저 ID, 메세지", required = true) DirectMessagePostReq directMessagePostReq) {
        directMessageService.sendDirectMessage(directMessagePostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/getChatRoomList/{userId}")
    @ApiOperation(value = "채팅방 목록", notes = "유저가 포함된 채팅방 목록을 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<ChatRoomListRes> getChatRoomList(@PathVariable Long userId) {
        List<ChatJoinInfo> chatJoinInfoList = directMessageService.getChatRoomList(userId);
        return ResponseEntity.status(200).body(ChatRoomListRes.of(chatJoinInfoList));
    }

    @GetMapping("/getChatMessageList/{chatRoomId}")
    @ApiOperation(value = "채팅 내역", notes = "채팅방 번호에 포함된 모든 메세지를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<ChatMessageListRes> getChatMessageList(@PathVariable Long chatRoomId) {
        List<ChatMessage> chatMessageList = directMessageService.getChatMessageList(chatRoomId);
        return ResponseEntity.status(200).body(ChatMessageListRes.of(chatMessageList));
    }
}
