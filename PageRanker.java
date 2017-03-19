import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PageRanker {
	private HashMap<String,RankNode> nodes;
	private String fileName;
	private Integer id;
	
	public PageRanker(String filename, int id) {
		nodes=new HashMap<>();
		this.fileName=filename;
		this.id=id;
	}
	/**
	 * Builds the map with incoming/outgoing nodes for pageRank
	 * @throws FileNotFoundException Scanner Error
	 */
	public void buildMap() throws FileNotFoundException {
		Scanner fileRead=new Scanner(new File(fileName)); 
		while (fileRead.hasNextLine()) {
			String line=fileRead.nextLine();
			String[] line_arr=line.split("[\\s+,]");
			String fromNode=line_arr[0];
			String toNode=line_arr[1];
			/*
			 * Create Nodes if Needed
			 */
			RankNode to,from;
			if (nodes.containsKey(fromNode)) {
				from=nodes.get(fromNode); //Use existing Node
			}
			else {
				from=new RankNode(fromNode); //Create New node
				nodes.put(from.getId(), from);
			}
			if (nodes.containsKey(toNode)) {
				to=nodes.get(toNode);
			}
			else {
				to=new RankNode(toNode);
				nodes.put(to.getId(), to);
			}
			/*
			 * Add outgoing and incoming edges
			 * Will not add if the connection is to itself
			 * Since incoming and outgoing are sets, duplicates will automatically be filtered out
			 */
			if (to.getId()!=from.getId()) {
				to.addIncomingNode(from);
				from.addOutgoingNode(to);
			}
			
		}
		fileRead.close();
	}
	/**
	 * Ranks the nodes
	 */
	public void RankNodes() {
		//This will keep track of if all the values are 'done' changing 
		HashMap<String,Boolean> ranksChanged=new HashMap<>();
		//Initialize the Set
		for(String s : nodes.keySet()) {
			ranksChanged.put(s, Boolean.TRUE);
		}
		Double RankSum=0.0,ReverseRankSum=0.0;

		while (ranksChanged.containsValue(Boolean.TRUE)) { //No changes
			//Keeps track of the rank and reverse rank sum for normalization. Resets to 0 each iteration
			RankSum=0.0;
			ReverseRankSum=0.0;
			for(RankNode n: nodes.values()) {
				//Updated Page Rank
				Boolean pRankChanged=n.updatePageRank();
				Boolean rRankChanged=n.updateReverseRank();
				//Update Map. Will change mapped value to false when both ranks stop changing 
				ranksChanged.put(n.getId(), (pRankChanged || rRankChanged)); 
				RankSum+=n.getPageRank();
				ReverseRankSum+=n.getReverseRank();
			}
		}
		/*
		 * Normalize
		 */
		
		for (RankNode n: nodes.values()) {
			n.setPageRank(n.getPageRank()/RankSum);
			n.setReverseRank(n.getReverseRank()/RankSum);
		} 
	}
	/**
	 * Literally Just writes the node and their page ranks to a file in alphabetical order
	 * @throws Exception
	 */
	public void createPageRankList() throws Exception {
		PrintWriter w=new PrintWriter(this.id+".txt");
		w.println("Page Ranks: ");
		List<String> names=nodes.keySet().stream().collect(Collectors.toList());
		Collections.sort(names);
		names.stream().forEach(n -> w.println(nodes.get(n).getStringRank()));
		w.println("Reverse Ranks: ");
		names.stream().forEach(n -> w.println(nodes.get(n).getStringReverseRank()));
		w.close();
	}

}
