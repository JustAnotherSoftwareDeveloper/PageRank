import java.io.FileNotFoundException;

public class PageRankHW {
	public static void main(String[] args) {
		
		
		for(int i=0; i<args.length; i++) {
			PageRanker p=new PageRanker(args[i],i);
			try {
				p.buildMap();
				p.RankNodes();
				p.createPageRankList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done");
	}
}
