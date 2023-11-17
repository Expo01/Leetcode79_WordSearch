import java.util.ArrayList;
import java.util.List;

// this is another editorial variation that i modifid to get rid of all the fields and 'this' calls to make it more like
// something i would write. timee still only beating 20%, but most important is that i can write it and its short
class Solution {

    public boolean exist(char[][] board, String word) {
        int ROWS = board.length;
        int COLS = board[0].length;

        for (int row = 0; row < ROWS; ++row)
            for (int col = 0; col < COLS; ++col)
                if (backtrack(row, col, word, 0,board))
                    return true;
        return false;
    }

    private boolean backtrack(int row, int col, String word, int index, char[][]board) {
        if (index >= word.length())
            return true;

        if (row < 0 || row == board.length || col < 0 || col == board[0].length || board[row][col] != word.charAt(index))
            return false;

        board[row][col] = '#'; // marker instead of 'visited' map

        int[] rowOffsets = {0, 1, 0, -1};
        int[] colOffsets = {1, 0, -1, 0};
        for (int d = 0; d < 4; ++d) {
            if (backtrack(row + rowOffsets[d], col + colOffsets[d], word, index + 1,board))
                return true;
        }

        board[row][col] = word.charAt(index); // reset to original char val since we must backtrack
        return false;
    }
}






// bottom of barrel for space and time. editorial solution 1
class Solution {
    private char[][] board;
    private int ROWS;
    private int COLS;

    public boolean exist(char[][] board, String word) {
        this.board = board;
        this.ROWS = board.length;
        this.COLS = board[0].length;

        for (int row = 0; row < this.ROWS; ++row)
            for (int col = 0; col < this.COLS; ++col)
                if (this.backtrack(row, col, word, 0))
                    return true;
        return false;
    }

    protected boolean backtrack(int row, int col, String word, int index) {
        /* Step 1). check the bottom case. */
        if (index >= word.length())
            return true;

        /* Step 2). Check the boundaries. */
        if (row < 0 || row == this.ROWS || col < 0 || col == this.COLS
                || this.board[row][col] != word.charAt(index))
            return false;

        /* Step 3). explore the neighbors in DFS */
        boolean ret = false;
        // mark the path before the next exploration
        this.board[row][col] = '#';

        int[] rowOffsets = {0, 1, 0, -1}; // this part is cool, finding a way to loop it instad of writing out 4 calls directly
        int[] colOffsets = {1, 0, -1, 0};
        for (int d = 0; d < 4; ++d) {
            ret = this.backtrack(row + rowOffsets[d], col + colOffsets[d], word, index + 1);
            if (ret)
                break;
        }

        /* Step 4). clean up and return the result. */
        this.board[row][col] = word.charAt(index);
        return ret;
    }
}








//  my solution, passes 46/86 cases. huge improvement. take this as a win.
class Pair{
    int m;
    int n;

    public Pair(int m, int n) {
        this.m = m;
        this.n = n;
    }
}

class Solution {
    public boolean exist(char[][] board, String word) {
        int index = 0;
        List<Pair> visited = new ArrayList<>();
        int row = 0;
        int column = 0;


        return DFS(board,word,index,row, column, visited);
    }

    private boolean DFS(char[][] board, String word, int index, int row, int column, List<Pair> visited){
        boolean wordFound = false;
        Pair cur = new Pair(row,column);
        if(visited.contains(cur)){
            return false;
        }
        if(row < 0 || row >= board.length || column < 0 || column >= board[0].length){
            return false;
        }
        char curChar = board[row][column];
        if(word.charAt(index) != curChar){
            return false;
        } else {
            if(index == word.length()-1){
                return true;
            }
            visited.add(cur);


            if(
                    DFS(board, word, index+1,row-1,column,visited) ||
                    DFS(board,word,index+1,row+1,column,visited) ||
                    DFS(board,word,index+1,row,column-1,visited) ||
                    DFS(board,word,index+1,row,column+1,visited) )   {
                wordFound = true;
            }
            if(!wordFound){
                visited.remove(cur);
            }
        }
        return wordFound;
    }
}

/*

suppose seedeez (nuts) is the word. backtracking needed, suppose we searched
left to right, top down. 's' explores to 'e' on the right, explores to 'e'
down eventually, then states word not found, but those e's needed later when
starting row index 2 column index 3 for letter 's' which completes word gooing
up first. there may be a way to store prefix subset of 'eez'or someething but
lets not go there for now

sede
zeoe
ooos*
odee

logic will be like this: if cell val = char, add cell coorinates to list of visited coordindates, 4 directionally check
for next char passing the word and the next char index with each recursion. if index = string.length then return true- word found
otherwise must remove coordinate pair froom visited. the index of the coordinate pair will always be the index of char being
searched for. 'leetcode' char t = index 3, means coorrdiatates 0,1,2 added, so if all 4 directions of cell explored,
then remove coordinate pair at index. dont have to pass index again since it already exists in the recursive call

 */