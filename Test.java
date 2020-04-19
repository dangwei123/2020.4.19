由 n 个连接的字符串 s 组成字符串 S，记作 S = [s,n]。例如，["abc",3]=“abcabcabc”。

如果我们可以从 s2 中删除某些字符使其变为 s1，则称字符串 s1 可以从字符串 s2 获得。例如，根据定义，"abc" 可以从 “abdbec” 获得，但不能从 “acbbe” 获得。

现在给你两个非空字符串 s1 和 s2（每个最多 100 个字符长）和两个整数 0 ≤ n1 ≤ 106 和 1 ≤ n2 ≤ 106。现在考虑字符串 S1 和 S2，其中 S1=[s1,n1] 、S2=[s2,n2] 。

请你找出一个可以满足使[S2,M] 从 S1 获得的最大整数 M 。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/count-the-repetitions
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        char[] arr=s1.toCharArray();
        char[] tmp=s2.toCharArray();
        int l1=s1.length();
        int l2=s2.length();
        int res=0;
        int index=0;
        for(int i=0;i<n1;i++){
            for(int j=0;j<l1;j++){
                if(arr[j]==tmp[index]){
                    if(++index==l2){
                        index=0;
                        res++;
                    }     
                }
            }
        }
        return res/n2;
    }
}

给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
class Solution {
    private int[] res;
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> list=new ArrayList<>();
        int n=nums.length;
        int[] index=new int[n];
        res=new int[n];
        for(int i=0;i<n;i++){
            index[i]=i;
        }
        mergeSort(nums,index,0,n-1);
        for(int i=0;i<n;i++){
            list.add(res[i]);
        }
        return list;
    }
    private void mergeSort(int[] nums,int[] index,int left,int right){
        if(left>=right){
            return;
        }
        int mid=left+(right-left)/2;
        mergeSort(nums,index,left,mid);
        mergeSort(nums,index,mid+1,right);
        merge(nums,index,left,mid,right);
    }
    private void merge(int[] nums,int[] index,int left,int mid,int right){
        int i=left;
        int j=mid+1;
        int len=right-left+1;
        int[] tmp=new int[len];
        int k=0;
        while(i<=mid&&j<=right){
            if(nums[index[i]]<=nums[index[j]]){
                res[index[i]]+=j-mid-1;
                tmp[k++]=index[i++];
            }else{
                tmp[k++]=index[j++];
            }
        }
        while(i<=mid){
            res[index[i]]+=right-mid;
            tmp[k++]=index[i++];
        }
        while(j<=right){
            tmp[k++]=index[j++];
        }
        System.arraycopy(tmp,0,index,left,len);
    }
}

给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。

返回 s 所有可能的分割方案。
class Solution {
    List<List<String>> res=new ArrayList<>();
    public List<List<String>> partition(String s) {
        back(s,0,s.length(),new ArrayList<>());
        return res;
    }
    private void back(String s,int begin,int len,List<String> row){
        if(begin==len){
            res.add(new ArrayList(row));
            return;
        }
        
        for(int i=begin;i<len;i++){
            if(isValid(s,begin,i)){
                row.add(s.substring(begin,i+1));
                back(s,i+1,len,row);
                row.remove(row.size()-1);
            }
        }
    }
    private boolean isValid(String s,int left,int right){
        while(left<right){
            if(s.charAt(left)!=s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}

给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。
class Solution {
    private List<String> res=new ArrayList<>();
    private int row;
    private int col;
    private int[] dx={0,0,1,-1};
    private int[] dy={1,-1,0,0};
    public List<String> findWords(char[][] board, String[] words) {
        row=board.length;
        if(row==0) return res;
        col=board[0].length;
        Trie trie=new Trie();
        for(String s:words){
            trie.insert(s);
        }
        Node root=trie.root;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                back(board,i,j,root,"",new boolean[row][col]);
            }
        }
        return res;
    }
    private void back(char[][]board,int i,int j,Node root,String s,boolean[][] v){
        if(i<0||j<0||i>=row||j>=col||v[i][j]==true){
            return;
        }
        int index=board[i][j]-'a';
        root=root.next[index];
        
        if(root==null){
            return;
        }
        
        s+=board[i][j];
        v[i][j]=true;
        if(root.isEnd==true){
            res.add(s);
            root.isEnd=false;
        }
        
        for(int k=0;k<4;k++){
            int x=i+dx[k];
            int y=j+dy[k];
            back(board,x,y,root,s,v);
        }
        s=s.substring(0,s.length()-1);
        v[i][j]=false;
    }
    
}


class Trie{
    public Node root;
    public Trie(){
        root=new Node();
    }
    
    public void insert(String s){
        Node cur=root;
        for(int i=0;i<s.length();i++){
            int index=s.charAt(i)-'a';
            if(cur.next[index]==null){
                cur.next[index]=new Node();
            }
            cur=cur.next[index];
        }
        cur.isEnd=true;
    }
}
class Node{
    public Node[] next;
    public boolean isEnd;
    public Node(){
        next=new Node[26];
    }
}

给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配
class Solution {
    public boolean isMatch(String s, String p) {
        int m=s.length();
        int n=p.length();
        boolean[][] dp=new boolean[m+1][n+1];
        dp[0][0]=true;
        
        for(int i=2;i<=n;i++){
            dp[0][i]=p.charAt(i-1)=='*'&&dp[0][i-2];
        }
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(p.charAt(j-1)=='*'){
                    dp[i][j]=isValid(s,p,i-1,j-2)&&dp[i-1][j]||dp[i][j-2];
                }else{
                    dp[i][j]=isValid(s,p,i-1,j-1)&&dp[i-1][j-1];
                }
            }
        }
        return dp[m][n];
    }
    private boolean isValid(String s,String p,int i,int j){
        if(s.charAt(i)==p.charAt(j)||p.charAt(j)=='.'){
            return true;
        }
        return false;
    }
}

给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
class Solution {
    public boolean isMatch(String s, String p) {
        int m=s.length();
        int n=p.length();
        boolean[][] dp=new boolean[m+1][n+1];
        dp[0][0]=true;
        for(int i=1;i<=n;i++){
            dp[0][i]=p.charAt(i-1)=='*'&&dp[0][i-1];
        }
        
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(s.charAt(i-1)==p.charAt(j-1)||p.charAt(j-1)=='?'){
                    dp[i][j]=dp[i-1][j-1];
                }else if(p.charAt(j-1)=='*'){
                    dp[i][j]=dp[i][j-1]||dp[i-1][j];
                }
            }
        }
        return dp[m][n];
    }
}

