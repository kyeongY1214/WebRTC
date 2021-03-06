package com.d203.backend.api.service.DirectMessage;

import com.d203.backend.api.request.DirectMessage.ChatRoomPostReq;
import com.d203.backend.api.request.DirectMessage.DirectMessagePostReq;
import com.d203.backend.db.entity.ChatJoinInfo;
import com.d203.backend.db.entity.ChatMessage;

import java.util.List;

public interface DirectMessageService {

    Long createChatRoom (ChatRoomPostReq chatRoomPostReq);

    boolean sendDirectMessage (DirectMessagePostReq directMessagePostReq);

    List<ChatJoinInfo> getChatRoomList (Long userId);

    List<ChatMessage> getChatMessageList (Long chatRoomId);

    Long getOpponentId (Long userId, Long chatRoomId);

    boolean deleteChatRoom(Long chatRoomId);
}
