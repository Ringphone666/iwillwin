package deque;

public class ArrayDeque<T> {
    int size;
    T items[];
    int first;
    int last;
    public ArrayDeque(){
        size=0;
        items=(T[]) new Object [8];
        first=items.length-1;
        last=0;
    }
    private void resize(int capacity) {
         T[] a = (T[]) new Object[capacity];
        for (int i = 0,j=1; i <size; i += 1,j++) {
            if(j+first<items.length)
                a[i] = items[j+first];
            else
                a[i]=items[j+first-size];
        }
        first = a.length-1;
        if(a.length>items.length)
            last = items.length;
        else
            last = a.length-1;
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
        size++;
        last=last== items.length-1?0:last+1;
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
        for(int j=1;j<=size;j++)
        {
            if(j+first<items.length)
                System.out.println(items[j+first]);
            else
                System.out.println(items[j+first- items.length]);
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
        size--;
        items[Remove]=null;
        return value;
    }
    public T removeFirst(){
        if(size==0)
            return null;
        if ((size < items.length / 4) && (items.length > 8)) {
            resize(items.length / 4);
        }
        int Remove=first==items.length-1?0:first+1;
        T value = items[Remove];
        size--;
        items[Remove]=null;
        first=Remove;
        return value;
    }
    public T get(int index)
    {
        return items[index];
    }
}
