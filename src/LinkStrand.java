public class LinkStrand implements IDnaStrand{

    //still need: 2 initializers, override toString

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


    public LinkStrand() { this("");}

    public LinkStrand(String s) { initialize(s);}

    @Override
    public long size() {
        return mySize;
    }

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

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna);
        myLast = myLast.next;
        mySize += dna.length();
        myAppends++;

        return this;
    }

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

    @Override
    public int getAppendCount() {
        return myAppends;
    }

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
