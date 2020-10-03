public class LinkStrand implements IDnaStrand{
    /**
     * Node class that makes up a linked list
     */
    private class Node {
        String info;
        Node next;
        public Node(String s) {
            info = s;
            next = null;
        }
    }

    private Node myFirst, myLast;
    private long mySize;            //num total characters in stored in all nodes together
    private int myAppends;          //num times append method called (num nodes in linked list - 1)
    private int myIndex;            //value of the parameter in the last call to charAt
    private int myLocalIndex;       //value of the index within the string stored in the
                                        // node last-referenced by charAt when the method finishes
    private Node myCurrent;

    /**
     * create an empty strand
     */
    public LinkStrand() { this("");}

    /**
     * create a new strand with s
     * @param s the value of the LinkStrand
     */
    public LinkStrand(String s) { initialize(s);}

    /**
     * returns the number of base pairs in the DNA strand
     * @return number of base pairs in entire strand
     */
    @Override
    public long size() {
        return mySize;
    }

    /**
     * Creates the strand so that it contains value source
     * @param source the value of the new strand
     */
    @Override
    public void initialize(String source) {
        myFirst = new Node(source);
        myLast = myFirst;
        mySize = source.length();
        myAppends = 0;

        myIndex = 0;
        myLocalIndex = 0;
        myCurrent = myFirst;
    }

    /**
     * returns LinkStrand with source bases
     * @param source is data from which object constructed
     * @return a LinkStrand with source bases
     */
    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    /**
     * appends dna to the end of the current LinkStrand by creating a new node with information dna
     * @param dna
     *            is the string appended to this strand
     * @return the LinkStrand with dna appended to the end of it
     */
    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna);
        myLast = myLast.next;
        mySize += dna.length();
        myAppends++;

        return this;
    }

    /**
     * returns a String containing all the base pairs in the LinkStrand
     * @return String of all base pairs
     */
    @Override
    public String toString() {
        Node curr = myFirst;
        StringBuilder string = new StringBuilder("");
        while (curr != null) {
            string.append(curr.info);
            curr = curr.next;
        }

        return string.toString();
    }

    /**
     * Creates a new LinkStrand with all base pairs reversed
     * @return new LinkStrand of reverse base pairs of this
     */
    @Override
    public IDnaStrand reverse() {
        LinkStrand rev = new LinkStrand();
        Node list = myFirst;

        boolean isFirst = true;
        while (list != null) {
            StringBuilder copy = new StringBuilder(list.info);
            copy.reverse();

            Node temp = rev.myFirst;
            rev.myFirst = new Node(copy.toString());
            if (isFirst) {
                rev.myLast = rev.myFirst;
            }

            rev.myFirst.next = temp;
            rev.mySize += copy.toString().length();
            rev.myAppends++;

            list = list.next;
        }

        return rev;
    }

    /**
     * gets the amount of times a strand has been appended to the LinkStrand
     * @return number of appends to this
     */
    @Override
    public int getAppendCount() {
        return myAppends;
    }

    /**
     * returns the base pair at location index of the LinkStrand
     * @param index specifies which character will be returned
     * @return base pair at the specified index
     */
    @Override
    public char charAt(int index) {
        if (index < myIndex) {
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }

        while (myIndex != index) {
            if (myLocalIndex == myCurrent.info.length() - 1 && myCurrent.next == null) {
                throw new IndexOutOfBoundsException("index is not valid");
            }

            myIndex++;
            myLocalIndex++;
            if (myLocalIndex >= myCurrent.info.length()) {
                myLocalIndex = 0;
                myCurrent = myCurrent.next;
            }
        }

        return myCurrent.info.charAt(myLocalIndex);
    }
}
