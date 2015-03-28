package org.mylivedata.app.dashboard.domain;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by lubo08 on 5.9.2014.
 */
@Entity
@Table(name = "users_chats", schema = "", catalog = "mylivedata")
public class UsersChatsEntity {
    private Integer chatId;
    private String initializedBy;
    private Timestamp start;
    private Timestamp end;
    private Integer satisfaction;
    private UsersEntity usersByUserId;
    private UsersEntity usersByInChatWithUserId;
    private Integer userId;
    private Integer inChatWithUserId;
    private Collection<ConversationEntity> conversationsByChatId;

    @Id
    @Column(name = "chat_id", nullable = false, insertable = true, updatable = true)
    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    @Basic
    @Column(name = "initialized_by", nullable = false, insertable = true, updatable = true, length = 7)
    public String getInitializedBy() {
        return initializedBy;
    }

    public void setInitializedBy(String initializedBy) {
        this.initializedBy = initializedBy;
    }

    @Basic
    @Column(name = "start", nullable = false, insertable = true, updatable = true)
    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    @Basic
    @Column(name = "end", nullable = false, insertable = true, updatable = true)
    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @Basic
    @Column(name = "satisfaction", nullable = true, insertable = true, updatable = true)
    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersChatsEntity that = (UsersChatsEntity) o;

        if (chatId != null ? !chatId.equals(that.chatId) : that.chatId != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (initializedBy != null ? !initializedBy.equals(that.initializedBy) : that.initializedBy != null)
            return false;
        if (satisfaction != null ? !satisfaction.equals(that.satisfaction) : that.satisfaction != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chatId != null ? chatId.hashCode() : 0;
        result = 31 * result + (initializedBy != null ? initializedBy.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (satisfaction != null ? satisfaction.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "in_chat_with_user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    public UsersEntity getUsersByInChatWithUserId() {
        return usersByInChatWithUserId;
    }

    public void setUsersByInChatWithUserId(UsersEntity usersByInChatWithUserId) {
        this.usersByInChatWithUserId = usersByInChatWithUserId;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "in_chat_with_user_id", nullable = false, insertable = true, updatable = true)
    public Integer getInChatWithUserId() {
        return inChatWithUserId;
    }

    public void setInChatWithUserId(Integer inChatWithUserId) {
        this.inChatWithUserId = inChatWithUserId;
    }

    @OneToMany(mappedBy = "usersChatsByChatId")
    public Collection<ConversationEntity> getConversationsByChatId() {
        return conversationsByChatId;
    }

    public void setConversationsByChatId(Collection<ConversationEntity> conversationsByChatId) {
        this.conversationsByChatId = conversationsByChatId;
    }
}
