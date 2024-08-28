package deque;

public class ArrayDeque<T> {
    int size;
    T items[];
    int first;
    int last;
    ArrayDeque(){
        int size=0;
        items=(T[]) new Object [8];
        int first=items.length;
        int last=0;
    }
    private void resize(int capacity) {
         T[] a = (T[]) new Object[capacity];
        for (int i = 0,j=0; i < size; i += 1,j++) {
            if(j+first<size)
                a[i] = items[j+first];
            else
                a[i]=items[j+first-size];
        }
        first = 0;
        last = items.length-1;
        items = a;

    }
    public void addFirst(T item){
        if(this.size==items.length){
            resize(this.size*2);
        }
        items[first]=item;
        size++;
        first=first==0?items.length-1:first-1;
    }
    public void addLast(T item){
        if(this.size==items.length){
            resize(this.size*2);
        }
        items[last]=item;
        last=last== items.length-1?0:last-1;
    }
    public boolean isEmpty(){
        if(size==0)
            return true;
        else
            return false;
    }
    public int size(){
        return this.size;
    }
    public void printDeque(){
        for(int j=0;j<size;j++)
        {
            if(j+first<size)
                System.out.println(items[j+first]);
            else
                System.out.println(items[j+first-size]);
        }
    }
    public T removeLast(){
        if(size==0)
            return null;
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        int Remove=last==0?items.length-1:last-1;
        T value = items[Remove];
        items[Remove]=null;
        return value;
    }
    public T removeFirst(){
        if(size==0)
            return null;
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        int Remove=first==items.length-1?0:first+1;
        T value = items[Remove];
        items[Remove]=null;
        return value;
    }
    public T get(int index)
    {
        return items[index];
    }
}
