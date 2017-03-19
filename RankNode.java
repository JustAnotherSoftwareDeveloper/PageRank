
import java.util.HashSet;

public class RankNode {

	/*
	 * The Node Id, be it a number or an arbitary String
	 */
	private String id;
	/*
	 * The Page Rank
	 */
	private Double pageRank;
	/*
	 * The rank of the graph reversed
	 */
	private Double reverseRank;
	/*
	 * The set of incoming and outgoing nodes
	 */
	private HashSet<RankNode> incoming,outgoing;
	/**
	 * Default Constructor. Creates new Node with ranks initialized to 1
	 * @param id: The name of the node
	 */
	public RankNode(String id) {
		this.id=id;
		pageRank=0.0;
		this.reverseRank=0.0;
		incoming=new HashSet<RankNode>();
		outgoing=new HashSet<RankNode>();
	}
	/**
	 * Simple Getter
	 * @return the Name of the node
	 */
	public String getId() {
		return id;
	}
	/**
	 * Simple Getter
	 * @return The page rank
	 */
	public Double getPageRank() {
		return this.pageRank;
	}
	/**
	 * Simple Getter
	 * @return the reverse rank
	 */
	public Double getReverseRank() {
		return this.reverseRank;
	}
	/**
	 * Updates the PageRank. 
	 * @return true if the pageRank changed, false otherwise
	 */
	public Boolean updatePageRank() {
		Double newRank=.85;
		Double toAdd=0.0;
		for(RankNode e: incoming) {
			toAdd+=(e.pageRank/(double)e.outgoing.size());
		}
		newRank+=(.15*toAdd);
		if (newRank.compareTo(this.pageRank)==0) { return false;}
		else {
			this.pageRank=newRank;
			return true;
		}
	}
	/**
	 * Updates the Reverse PageRank. 
	 * @return true if the reverseRank changed, false otherwise
	 */
	public Boolean updateReverseRank() {
		Double newRank=.85;
		Double toAdd=0.0;
		for(RankNode e: outgoing) {
			toAdd+=(e.reverseRank/ (double) e.incoming.size());
		};
		newRank+=(.15*toAdd);
		if (newRank.compareTo(this.reverseRank)==0) { return false;}
		else {
			this.reverseRank=newRank;
			return true;
		}
	}
	/**
	 * toString() for pageRank
	 * @return String formated as NAME: PAGERANK
	 */
	public String getStringRank() {
		double roundedRank=Math.round(this.pageRank*1000.0)/1000.0;
		StringBuilder s=new StringBuilder();
		s.append(id);
		s.append(": ");
		s.append(roundedRank);
		return s.toString();
	}
	/**
	 * toString() for reverseRank
	 * @return String formated as NAME: REVERSERANK 
	 */
	public String getStringReverseRank() {
		double roundedRank=Math.round(this.reverseRank*1000.0)/1000.0;
		StringBuilder s=new StringBuilder();
		s.append(id);
		s.append(": ");
		s.append(roundedRank);
		return s.toString();
	}
	/**
	 * Adds Node
	 * @param n RankNode to add
	 */
	public void addIncomingNode(RankNode n) {
		this.incoming.add(n);
	}
	/**
	 * Adds node to outgoing set
	 * @param n node to add
	 */
	public void addOutgoingNode(RankNode n) {
		this.outgoing.add(n);
	}
	/**
	 * Sets page rank, used for normalization
	 * @param d new pagerank
	 */
	public void setPageRank(Double d) {
		this.pageRank=d;
	}
	/**
	 * Sets reverse page rank, used for normalization
	 * @param d new reverse page rank
	 */
	public void setReverseRank(Double d) {
		this.reverseRank=d;
	}
	
	
	
}
