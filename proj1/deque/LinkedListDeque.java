package deque;

public class LinkedListDeque<T> implements Deque<T> {
    class Dlist<T>{
        T value;
        Dlist<T> next;
        Dlist<T> prev;

        public Dlist(T i) {
            this.value=i;
        }

    }
    int size;
    Dlist<T> sentFront;
    Dlist<T> sentBack;
    public LinkedListDeque()
    {
        sentFront=new Dlist<>(null);
        sentBack=new Dlist<>(null);
        sentFront.next=sentBack;
        sentBack.prev=sentFront;
    }
    public void addFirst(T i)
    {
        if(size==0)
        {
            size++;
            Dlist <T>newitem=new Dlist(i);
            newitem.prev=sentFront;
            newitem.next=sentBack;
            sentBack.prev=newitem;
            sentFront.next=newitem;
        }
        else
        {
            size++;
            Dlist<T>newitem=new Dlist<>(i);
            newitem.next=sentFront.next;
            sentFront.next.prev=newitem;
            sentFront.next=newitem;
        }
    }
    public void addLast(T i){
        if(size==0)
        {
            size++;
            Dlist <T>newitem=new Dlist(i);
            newitem.prev=sentFront;
            newitem.next=sentBack;
            sentBack.prev=newitem;
            sentFront.next=newitem;
        }
        else
        {
            size++;
            Dlist<T>newitem=new Dlist<>(i);
            newitem.prev=sentBack.prev;
            sentBack.prev.next=newitem;
            sentBack.prev=newitem;
        }
    }
    public int size(){
        return this.size;
    }
    @Override
    public boolean isEmpty(){
        if(this.size==0)
            return true;
        else
            return false;
    }
    public void printDeque()
    {
        Dlist temp=sentFront;
        while(temp.next.value!=null)
        {
           System.out.println(temp.next.value);
           temp=temp.next;
        }
    }
    public T removeFirst(){
        if(this.size==0)
            return null;
        T item = sentFront.next.value;
        sentFront.next=sentFront.next.next;
        sentFront.next.prev=sentFront;
        System.out.println(item);
        size--;
        return item;
    }
    public T removeLast(){
        if(this.size==0)
            return null;
        T item = sentBack.prev.value;
        sentBack.prev=sentBack.prev.prev;
        sentBack.prev.next=sentBack;
        size--;
        return item;
    }
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        Dlist<T> currentNode = sentFront.next;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return currentNode.value;
            }
        }
        return null;
    }
    public T getRecursive(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        return getRecursiveHelper(index, sentFront.next);
    }

    private T getRecursiveHelper(int index, Dlist<T> currentNode) {
        if (index == 0) {
            return currentNode.value;
        }
        return getRecursiveHelper(index - 1, currentNode.next);
    }
}
