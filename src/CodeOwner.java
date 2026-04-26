import java.util.ArrayList;

abstract class Ticket {
    protected String id;
    protected String comment;

    public abstract String setId();

    public abstract String setComment(String comments);
}

class ComplainTicket extends Ticket{
    private String type;
    private static int noOfTickets =0;

    public ComplainTicket() {
        noOfTickets++;
        this.id ="CT-"+ "00"+ noOfTickets;
    }
    public static int getnoOfTicket(){ return noOfTickets;}


    public String getId() {
        return id;
    }

    @Override
    public String setId() {
        return "";
    }

    @Override
    public String setComment(String comments) {
        return this.comment=comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "[Complaint] " +
                "id='" + id + '\'' +
                ", comment='" + comment + '\''
                +", type='" + type + '\'';
    }


    public String toStringId() {
        return "ComplainTicket " + id;
    }
}
 class FeedbackTicket extends Ticket{
    int rating;
    private static int noOfFeedback = 0;

    public FeedbackTicket() {
        noOfFeedback++;
    }

    public int getNoOfFeedback(){ return noOfFeedback; }

    @Override
    public String setId() {
        return "FB-"+"00"+getNoOfFeedback();
    }

    @Override
    public String setComment(String comments) {
        return this.comment = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5){
            this.rating = rating;
        }else System.out.println("Please rate form 1-5");
    }

    @Override
    public String toString() {
        return "[Feedback] " +
                "id='" + setId() + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating;
    }

    public String toStringIdF() {
        return "FeedbackTicket " + setId();
    }
}

public class CodeOwner {
    private static ArrayList<Ticket> tickets = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("the number of complines:" + ComplainTicket.getnoOfTicket());

        ComplainTicket t1 = new ComplainTicket();
        ComplainTicket t2 = new ComplainTicket();
        FeedbackTicket f1 = new FeedbackTicket();

        System.out.println("---created ticket----");
        System.out.println(t1.toStringId() + " added");
        System.out.println(t2.toStringId() + " added");
        System.out.println(f1.toStringIdF() + " added");
        System.out.println("the number of complines:" + ComplainTicket.getnoOfTicket());

        t1.setComment("Too laud");
        t1.setType("sound issue");
        System.out.println(t1.toString());

        t2.setComment("To mush heat");
        t2.setType("cooling issue");
        System.out.println(t2.toString());


        f1.setComment("Great app!");
        f1.setRating(5);
        System.out.println(f1.toString());

        tickets.add(t1);
        tickets.add(t2);
        tickets.add(f1);
    }
}