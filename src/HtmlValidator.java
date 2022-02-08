import java.util.Queue;
import java.util.*;

/**
 * Add your own comments
 */

public class HtmlValidator {
    private Queue<HtmlTag> tags;

    public HtmlValidator() {

        tags = new LinkedList<>();
    }

    public HtmlValidator(Queue<HtmlTag> tags) {
        if (tags == null) {
            throw new IllegalArgumentException("Initial tags cannot be null.");
        }

        //do I need line 22??
        tags = copy(tags);
        this.tags = new LinkedList<>(tags);
    }

    //adds the given tag to end of validator's queue
    //if null throws exception
    public void addTag(HtmlTag tag) {
        if(tag == null) {
            throw new IllegalArgumentException("Initial tags cannot be null");
        }
        tags.add(tag);
    }

    //returns validator's queue of HTML tags
    public Queue<HtmlTag> getTags() {

        return copy(tags);
    }


    //remove all tags matching element
    public void removeAll(String element) {
        if (element == null) throw new IllegalArgumentException();
        tags.removeIf(tag -> tag.getElement().equalsIgnoreCase(element));
    }

    public void validate(){
        Queue<HtmlTag>copyQ = copy(tags);
        Stack<HtmlTag> tagsStack = new Stack<HtmlTag>();
        int indent = 0;
        while (!copyQ.isEmpty()) {
            HtmlTag t = copyQ.remove();
            if(!t.isOpenTag()) {
                if (!tagsStack.isEmpty() && t.matches(tagsStack.peek())) {
                    indent--;
                    printTag(indent,t);
                    tagsStack.pop();
                } else{
                    System.out.println("ERROR unexpected tag:" + t);
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
            System.out.println("ERROR unclosed tag"+ tag);

        }
    }
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

    private void printTag(int n, HtmlTag tag) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
        System.out.println(tag);
    }
    }

