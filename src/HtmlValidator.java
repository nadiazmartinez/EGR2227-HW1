import java.util.Queue;
import java.util.*;

/**
 * Add your own comments
 */

public class HtmlValidator {
    private Queue<HtmlTag> tags; //default constructor

    //empty queue (Linked list) stores HTML tags
    public HtmlValidator() { //first constructor

        tags = new LinkedList<>(); //initializes empty queue
    }

    //initializes validator with a separate copy of the queue that was passed in
    public HtmlValidator(Queue<HtmlTag> tags) { //second constructor
        if (tags == null) {
            throw new IllegalArgumentException("Initial tags cannot be null."); //error message
        }

        this.tags = new LinkedList<>(tags); //initializes queue with input from parameters
    }

    //adds the given tag to end of validator's queue
    //if null throws exception
    public void addTag(HtmlTag tag) {
        if(tag == null) {
            throw new IllegalArgumentException("Initial tags cannot be null"); //error message for empty tag
        }
        tags.add(tag);
    }

    //returns validator's queue of HTML tags
    public Queue<HtmlTag> getTags() { //queue contains all tags + reflects any changes

        return copy(tags);
    }


    //remove all tags with string element
    public void removeAll(String element) {
        if (element == null) throw new IllegalArgumentException();
        tags.removeIf(tag -> tag.getElement().equalsIgnoreCase(element));
    }

    //prints indented text representation of the HTML tags in the queue and validates HTML file
    //calls helper method printTag
    public void validate(){
        Queue<HtmlTag>copyQ = copy(tags); //copy of queue for HTML file input
        Stack<HtmlTag> tagsStack = new Stack<HtmlTag>(); //analyzes queue
        int indent = 0;
        while (!copyQ.isEmpty()) {
            HtmlTag t = copyQ.remove();
            if(!t.isOpenTag()) {
                if (!tagsStack.isEmpty() && t.matches(tagsStack.peek())) {
                    indent--;
                    printTag(indent,t); //prints indentation + tag
                    tagsStack.pop();
                } else{
                    System.out.println("ERROR unexpected tag:" + t); //error message
                }
            } else {
                printTag(indent, t);
                if(!t.isSelfClosing()){
                    tagsStack.add(t);
                    indent++;
                }
            }
        }
        while(!tagsStack.isEmpty()){
            HtmlTag tag = tagsStack.pop();
            System.out.println("ERROR unclosed tag"+ tag); //error for unclosed tag

        }
    }

    //helper method
    //evaluates tags returns copy of queue or null
    private Queue<HtmlTag> copy(Queue<HtmlTag>tags){
        if(tags != null){
            Queue<HtmlTag> copyQ = new LinkedList<HtmlTag>();
            int size = tags.size();
            for(int i = 0; i < size; i++){
                HtmlTag ht = tags.remove();
                copyQ.add(ht);
                tags.add(ht);
            }
            return copyQ;
        }
        return null;
    }

    //helper method
    //prints indentation then tag
    private void printTag(int n, HtmlTag tag) {  //two constructors
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
        System.out.println(tag);
    }
    }

