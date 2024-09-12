package bstmap;

import java.util.Comparator;
import java.util.Set;
import java.util.Iterator;
public class BSTMap<K  extends Comparable<K>, V> implements Map61B<K, V> {
    public class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node(K key,V value){
            this.key=key;
            this.value=value;
        }
    }
    private Node root;
    int size=0;
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
    @Override
    public void clear() {
        root = null ;
        size =0 ;
    }

    @Override
    public boolean containsKey(K key) {
        return  containsKey(root , key);
    }
    public boolean containsKey(Node item,K key)
    {
        if(item==null)
            return false;
        if (item.key==key) {
            return true;
        }
        if (key.compareTo(item.key)<0) {
            return containsKey(item.left,key);
        } else {
            return containsKey(item.right,key);
        }
    }

    @Override
    public V get(K key) {
        return get(root,key);
    }
    public V get(Node node,K key){
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        }
        return node.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
         root=put(root,key,value);
         size++;
    }
    public Node put(Node item,K key,V value){
        if(item==null)
        {
            return new Node(key,value);
        }
        int cmp = key.compareTo(item.key);
        if(cmp<0)
            item.left = put(item.left,key,value);
        else
            item.right = put (item.right,key,value);
        return item;
    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }
}
