public class logic_block_test {
	
	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		
		rt.gc();
		logic_block lb = new logic_block();
		int i = lb.test_start("logic");
		int j = lb.test_end("heart");
		long mem1 = rt.totalMemory() - rt.freeMemory();
		System.out.printf("%d %d\n",i,j);
		
		Node test = lb.ASTAR();
		long mem2 = rt.totalMemory() - rt.freeMemory();
		
		if (test != null) {
			System.out.println(test.getTrail());
		}else {
			System.out.println("NULL");
		}
		System.out.printf("%d\n",mem2-mem1);
	}
}
