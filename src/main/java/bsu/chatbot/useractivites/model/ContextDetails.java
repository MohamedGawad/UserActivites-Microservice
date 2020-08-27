package bsu.chatbot.useractivites.model;

import java.io.Serializable;

import com.sbm.api.messenger.messeges.received.AttachmentPaylodRe;

public class ContextDetails implements Serializable {
	/**
	 * @author Mohamed Gawad
	 *
	 *         SBM-API
	 */
	private static final long serialVersionUID = 1268845000795762990L;

	private String senderID;
	private String currentPayload;
	private String lastPayload;
	private long lastAccessTime;
	private boolean authenticated;
	private String backendSessionId;
	private long backendLastCallTime;
	private String locale;
	private AttachmentPaylodRe CurrentAttachment;
	private boolean notificationEnabled;

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getCurrentPayload() {
		return currentPayload;
	}

	public void setCurrentPayload(String currentPayload) {
		this.currentPayload = currentPayload;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String getLastPayload() {
		return lastPayload;
	}

	public void setLastPayload(String lastPayload) {
		this.lastPayload = lastPayload;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
			this.authenticated = authenticated;
	}

	public String getBackendSessionId() {
		return backendSessionId;
	}

	public void setBackendSessionId(String backendSessionId) {
		this.backendSessionId = backendSessionId;
	}

	public long getBackendLastCallTime() {
		return backendLastCallTime;
	}

	public void setBackendLastCallTime(long backendLastCallTime) {
		this.backendLastCallTime = backendLastCallTime;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public AttachmentPaylodRe getCurrentAttachment() {
		return CurrentAttachment;
	}

	public void setCurrentAttachment(AttachmentPaylodRe currentAttachment) {
		CurrentAttachment = currentAttachment;
	}

	public boolean isNotificationEnabled() {
		return notificationEnabled;
	}

	public void setNotificationEnabled(boolean notificationEnabled) {
		this.notificationEnabled = notificationEnabled;
	}
}
