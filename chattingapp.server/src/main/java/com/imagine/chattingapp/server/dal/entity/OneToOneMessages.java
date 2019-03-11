package com.imagine.chattingapp.server.dal.entity;
// Generated Mar 11, 2019 12:40:18 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * OneToOneMessages generated by hbm2java
 */
@Entity
@Table(name="one_to_one_messages"
    ,catalog="chattingapp"
)
public class OneToOneMessages  implements java.io.Serializable {


     private OneToOneMessagesId id;
     private Date timestamp;
     private User userBySenderPhoneNumber;
     private User userByReceiverPhoneNumber;
     private String messageContentOrFileName;
     private byte[] file;
     private byte status;

    public OneToOneMessages() {
    }

	
    public OneToOneMessages(OneToOneMessagesId id, User userBySenderPhoneNumber, User userByReceiverPhoneNumber, String messageContentOrFileName, byte status) {
        this.id = id;
        this.userBySenderPhoneNumber = userBySenderPhoneNumber;
        this.userByReceiverPhoneNumber = userByReceiverPhoneNumber;
        this.messageContentOrFileName = messageContentOrFileName;
        this.status = status;
    }
    public OneToOneMessages(OneToOneMessagesId id, User userBySenderPhoneNumber, User userByReceiverPhoneNumber, String messageContentOrFileName, byte[] file, byte status) {
       this.id = id;
       this.userBySenderPhoneNumber = userBySenderPhoneNumber;
       this.userByReceiverPhoneNumber = userByReceiverPhoneNumber;
       this.messageContentOrFileName = messageContentOrFileName;
       this.file = file;
       this.status = status;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="senderPhoneNumber", column=@Column(name="sender_phone_number", nullable=false, length=16) ), 
        @AttributeOverride(name="receiverPhoneNumber", column=@Column(name="receiver_phone_number", nullable=false, length=16) ) } )
    public OneToOneMessagesId getId() {
        return this.id;
    }
    
    public void setId(OneToOneMessagesId id) {
        this.id = id;
    }

    @Version@Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp", nullable=false, length=26)
    public Date getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sender_phone_number", nullable=false, insertable=false, updatable=false)
    public User getUserBySenderPhoneNumber() {
        return this.userBySenderPhoneNumber;
    }
    
    public void setUserBySenderPhoneNumber(User userBySenderPhoneNumber) {
        this.userBySenderPhoneNumber = userBySenderPhoneNumber;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receiver_phone_number", nullable=false, insertable=false, updatable=false)
    public User getUserByReceiverPhoneNumber() {
        return this.userByReceiverPhoneNumber;
    }
    
    public void setUserByReceiverPhoneNumber(User userByReceiverPhoneNumber) {
        this.userByReceiverPhoneNumber = userByReceiverPhoneNumber;
    }

    
    @Column(name="message_content_or_file_name", nullable=false, length=65535)
    public String getMessageContentOrFileName() {
        return this.messageContentOrFileName;
    }
    
    public void setMessageContentOrFileName(String messageContentOrFileName) {
        this.messageContentOrFileName = messageContentOrFileName;
    }

    
    @Column(name="file")
    public byte[] getFile() {
        return this.file;
    }
    
    public void setFile(byte[] file) {
        this.file = file;
    }

    
    @Column(name="status", nullable=false)
    public byte getStatus() {
        return this.status;
    }
    
    public void setStatus(byte status) {
        this.status = status;
    }




}


