package ace.voidapps.gamifiedhabittracker.model;

public class Feedback {

	private int feedbackId;
	private String title;
	private Client client;
	private String content;

	public Feedback(int feedbackId, String title, Client client, String content) {
		this.feedbackId = feedbackId;
		this.title = title;
		this.client = client;
		this.content = content;
	}

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
