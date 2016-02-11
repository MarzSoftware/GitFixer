
public class GitConflict {

	public static String HEADER = "<<<<<<< HEAD";
	public static String TAIL = ">>>>>>>";
	public static String MIDDLE = "=======";
	
	private int startIndex;
	private int endIndex;
	private String head;
	private String tail;
	private String commitID;
	private String solution;
	
	public GitConflict(int startIndex, String head, String tail, String commitID, int endIndex) {
		this.startIndex = startIndex;
		this.head = head;
		this.tail = tail;
		this.commitID = commitID;
		this.endIndex = endIndex;
		solution = "";
	}
	
	/**
	 * @return the commitID
	 */
	public String getCommitID() {
		return commitID;
	}

	/**
	 * @return the solution
	 */
	public String getSolution() {
		return solution;
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(String solution) {
		this.solution = solution;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}
	
	/**
	 * @return the head
	 */
	public String getHead() {
		return head;
	}

	/**
	 * @return the tail
	 */
	public String getTail() {
		return tail;
	}
	
	public String toString() {
		return HEADER + "\n" + head + "\n" + MIDDLE + "\n" + TAIL + "\n";
	}

	public boolean tryToResolve() {
		boolean result = false;
		if (head.length() <= 0) {
			solution = head;
			result = true;
		} else if (tail.length() <= 0) {
			solution = tail;
			result = true;
		}
		return result;
	}
}
