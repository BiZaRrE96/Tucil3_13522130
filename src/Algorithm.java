import java.util.ArrayList;
import java.util.List;

public class Algorithm {
	public static DictionarySeeker DS = null;
	public static int last_node_visits = 0;
	
	public static Node UCS(String start,String end) {
		NodePrioQueue prioq = new NodePrioQueue(start);
		List<String> banlist = new ArrayList<String>();
		
		Node actor = null;
		last_node_visits = 0;
		boolean found = false;
		while (!found) {
			if (prioq.size() == 0) {
				return null;
			}
			actor = prioq.pop();
			last_node_visits++;
			if (StringOperator.CharDiff(actor.getName(),end) == 0 || actor.getName().equalsIgnoreCase(end)) {
				System.out.println("FOUND");
				System.out.println(actor.getName());
				System.out.println(actor.getTrail());
				return actor;
			}
			else {
				//PREACH PROGRAM PREACH
				System.out.printf("NOW OPERATING %s (Score : %d),%d\n",actor.getName(),actor.getTrail().size(),last_node_visits);
				
				//find every possible combination and push to queue
				for (String word : DS.exposeWords()) {
					if (StringOperator.CharDiff(actor.getName(), word) != 1) {
						continue;
					}
					
					//NORMAL PRIOQ
					if (banlist.indexOf(word) != -1) {
						continue;
					}
					
					banlist.add(word);
					prioq.add(new Node(actor,word));
				}
			}	
		}
		
		//PLACEHOLDER
		return null;
	}
	
	public static Node GBFS(String start,String end) {
		NodePrioQueue prioq = new AltNodePrioQueue(start);
		Node actor = null;
		last_node_visits = 0;
		List<String> banpool = new ArrayList<String>();
		boolean found = false;
		while (!found) {
			if (prioq.size() == 0) {
				return null;
			}
			
			actor = prioq.pop();
			last_node_visits++;
			if (actor.getName().trim() == end || actor.getScore() == end.length()) {
				System.out.println("FOUND");
				System.out.println(actor.getName());
				System.out.println(actor.getTrail());
				found = true;
			}
			else {
				//PREACH PROGRAM PREACH
				System.out.printf("NOW OPERATING %s (Score : %d)\n",actor.getName(),actor.getScore());
				
				//find every possible combination and push to queue
				for (String word : DS.exposeWords()) {
				
						
					//see if only 1 letter different
					if (StringOperator.CharDiff(word, actor.getName()) == 1) {
						//System.out.printf("%s & %s (%d)\n",word,actor.getName(),actor.getSize());
						if (banpool.indexOf(word) != -1) {
							continue;
						}
						//NORMAL PRIOQ
						//prioq.add(new Node(actor,word));
						//ALT PRIOQ
						prioq.add(new Node(actor,word).setScore(word.length() - StringOperator.CharDiff(word,end)));
						banpool.add(word);
						}
					}	
				}
			}	
			return actor;
	}
	
	public static Node AStar(String start,String end) {
		NodePrioQueue queue = new AltNodePrioQueue();
		
		//in the beninging
		queue.add(new Node(start).setScore(start.length() - StringOperator.CharDiff(start, end) + 0));
		last_node_visits = 0;
		boolean found = false;
		Node actor = null;
		
		List<String> banpool = new ArrayList<String>();
		
		while (!found && queue.size() > 0) {
			if (queue.size() == 0) {
				return null;
			}
			actor = queue.pop();
			last_node_visits++;
			
			if (actor.getName().equalsIgnoreCase(end)) {
				found = true;
				System.out.println(actor.getTrail());
			}
			else {
				//check neighbours
				for (String word : DS.exposeWords()) {
					if (StringOperator.CharDiff(word, actor.getName()) == 1 && !banpool.contains(word)) {
						int distance = actor.getTrail().size() + 1;
						int heuristic = word.length() - StringOperator.CharDiff(word, end);
						if (banpool.indexOf(word) == -1) {
							queue.add(new Node(actor,word).setScore(distance + heuristic));
							banpool.add(word);
							System.out.printf("ADD, %s (%d | %d)\n",word,distance,heuristic);
						}
					}
				}
			}
			
//			System.out.printf("REPORT, size %d, lowest %d\n",queue.size(),queue.exposeList().get(0).getScore());
			
		}
		
		if (found) {
			return actor;
		}
		else {
			return null;
		}
		
	}
}
