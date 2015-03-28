package org.mylivedata.app.dashboard.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by lubo08 on 9.3.2014.
 */
@Entity
@Table(name = "conversation", schema = "", catalog = "mylivedata")
public class ConversationEntity {
    private Integer conversationId;
    private Integer chatId;
    private String messageBy;
    private String messageText;
    private UsersChatsEntity usersChatsByChatId;

    @Id
    @Column(name = "conversation_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    @Basic
    @Column(name = "chat_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    @Basic
    @Column(name = "message_by", nullable = true, insertable = true, updatable = true, length = 7, precision = 0)
    public String getMessageBy() {
        return messageBy;
    }

    public void setMessageBy(String messageBy) {
        this.messageBy = messageBy;
    }

    @Basic
    @Column(name = "message_text", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConversationEntity that = (ConversationEntity) o;

        if (chatId != null ? !chatId.equals(that.chatId) : that.chatId != null) return false;
        if (conversationId != null ? !conversationId.equals(that.conversationId) : that.conversationId != null)
            return false;
        if (messageBy != null ? !messageBy.equals(that.messageBy) : that.messageBy != null) return false;
        if (messageText != null ? !messageText.equals(that.messageText) : that.messageText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = conversationId != null ? conversationId.hashCode() : 0;
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (messageBy != null ? messageBy.hashCode() : 0);
        result = 31 * result + (messageText != null ? messageText.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id", nullable = false, insertable = false, updatable = false)
    public UsersChatsEntity getUsersChatsByChatId() {
        return usersChatsByChatId;
    }

    public void setUsersChatsByChatId(UsersChatsEntity usersChatsByChatId) {
        this.usersChatsByChatId = usersChatsByChatId;
    }
}
