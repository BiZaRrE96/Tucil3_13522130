import java.util.ArrayList;
import java.util.List;

class Node {
    private String name;
    private ArrayList<String> trail;
    private int score = 0;

    public Node(String name) {
        this.name = name;
        trail = new ArrayList<>();
    }

    public Node(Node node, String name) {
        this.name = name;
        this.trail = new ArrayList<>(node.getTrail());
        this.trail.add(node.getName());
    }
    
    public Node setScore(int n) {
    	this.score = n;
    	return this;
    }
    
    public int getScore() {
    	return this.score;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getTrail() {
        return trail;
    }

    public int getSize() {
        return trail.size();
    }
}

class NodePrioQueue {
    protected List<Node> queue;

    public NodePrioQueue() {
        this.queue = new ArrayList<>();
    }

    public NodePrioQueue(String start) {
        this.queue = new ArrayList<>();
        this.queue.add(new Node(start));
    }

    public Node pop() {
        return this.queue.remove(0);
    }

    public void add(Node item) {
        if (this.queue.isEmpty()) {
            this.queue.add(item);
            return;
        }

        int i = 0;
        while (i < this.queue.size() && this.queue.get(i).getSize() <= item.getSize()) {
            i++;
        }
        this.queue.add(i, item);
    }
    
    public List<Node> exposeList(){
    	return this.queue;
    }
    
    public int size() {
    	return this.queue.size();
    }
    
    public boolean exists(String name) {
    	for (Node n : this.queue) {
    		if (n.getName().equalsIgnoreCase(name)) {
    			return true;
    		}
    	}
    	return false;
    }
}

class AltNodePrioQueue extends NodePrioQueue{

    public AltNodePrioQueue() {
    	super();
    }

    public AltNodePrioQueue(String start) {
        super(start);
    }
    
    @Override
    public void add(Node item) {
        if (queue.isEmpty()) {
            queue.add(item);
            return;
        }

        int i = 0;
        while (i < queue.size() && queue.get(i).getScore() >= item.getScore()) {
            i++;
        }
        queue.add(i, item);
    }
    
}