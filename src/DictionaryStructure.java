import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shrey on 11/13/2017.
 */
public class DictionaryStructure {
    //scrabble dictionary tree structure
    Trie ScrabbleDictionaryTrie;

    /**
     * Reads the scrabble dictionary text file into the Trie data structure
     */
    public void readInDictionary() {
        ScrabbleDictionaryTrie = new Trie();
        File file = new File("ScrabbleDictionary.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null){
                ScrabbleDictionaryTrie.insert(line);
            }
        }
        catch (IOException e){
            System.out.print(e.getStackTrace());
        }
    }

    /**
     * Reads the scrabble dictionary text file into a Trie data structure where all the words are reversed (use case:
     * search for a suffix instead of a prefix by reversing the suffix and searching for it in this trie)
     */
    public void reverseReadInDictionary() {
        ScrabbleDictionaryTrie = new Trie();
        File file = new File("ScrabbleDictionary.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null){
                ScrabbleDictionaryTrie.insert(new StringBuilder(line).reverse().toString());
            }
        }
        catch (IOException e){
            System.out.print(e.getStackTrace());
        }
    }
}

//node data structure that makes up the Trie dictionary
class Node{

    //character at Trie node
    char character;

    //children of node (next letter in string)
    HashMap<Character, Node> children = new HashMap<>();

    //whether or not node is the end of a word
    boolean isLeaf;

    /**
     * constructor for a node with a character value
     * @param newCharacter character value of node
     */
    public Node(char newCharacter){
        character = newCharacter;
    }

    /**
     * root node constructor
     */
    public Node(){}
}

//Trie data structure for dictionary
class Trie{

    //root node of dictionary
    Node root;

    //sets an empty "root" node as a starting point to the Trie
    public Trie(){
        root = new Node();
    }

    /**
     * Inserts a word into the Scrabble dictionary trie
     * @param word word to insert into dictionary trie
     */
    public void insert(String word){
        HashMap<Character, Node> children = root.children;

        for (int i = 0; i < word.length(); i++){
            char charAtIndex = word.charAt(i);

            Node currNode;
            if (children.containsKey(charAtIndex)){
                currNode = children.get(charAtIndex);
            }
            else{
                currNode = new Node(charAtIndex);
                children.put(charAtIndex, currNode);
            }

            children = currNode.children;

            if (i==word.length()-1)
                currNode.isLeaf = true;
        }
    }

    /**
     * searches for a word in the scrabble dictionary
     * @param word word searched for in dictionary
     * @return whether or not the word is in the dictionary
     */
    public boolean search(String word){
        Node finalLetterNode = searchNode(word);

        if (finalLetterNode!=null && finalLetterNode.isLeaf)
            return true;
        else
            return false;
    }

    /**
     * searches whether a word with the given prefix exists within the dictionary: to be used only with the forwards
     * dictionary
     * @param prefix prefix we are looking for in dictionary
     * @return the last node of the prefix of the first word that has this prefix (if it exists; null if it doesn't)
     */
    public Node searchPrefix(String prefix){
        Node finalLetterNode = searchNode(prefix);

        if (finalLetterNode!=null){
            return finalLetterNode;
        }
        else
            return null;
    }

    /**
     * searches whether a word with the given suffix exists within the dictionary: to be used only with the reversed
     * dictionary
     * @param suffix suffix we are looking for in the dictionary
     * @return the last node of the reversed suffix in the reversed tree of the first word that has this suffix (if such a word exists; null if it doesn't)
     */
    public Node searchSuffix(String suffix){
        String reverseSuffix = new StringBuilder(suffix).reverse().toString();
        Node finalLetterNode = searchNode(reverseSuffix);

        if (finalLetterNode!=null){
            return finalLetterNode;
        }
        else
            return null;
    }

    /**
     * traverses down through each character in the word string in order to find the last node in the chain of letters
     * @param str sequence of letters to traverse down
     * @return last node in sequence of letters, null if the next letter is not found
     */
    public Node searchNode (String str){
        Map<Character, Node> children = root.children;
        Node currentNode = null;
        for (int i = 0; i < str.length(); i++){
            char nodeChild = str.charAt(i);
            if (children.containsKey(nodeChild)){
                currentNode = children.get(nodeChild);
                children = currentNode.children;
            }
            else{
                return null;
            }
        }
        return currentNode;
    }
}

