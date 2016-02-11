public interface MergeViewListener {
		void keepHead(GitConflict gc);
		void keepTail(GitConflict gc);
		void loadFile(String filename);
}