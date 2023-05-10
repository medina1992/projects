import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Homework53 {
    public static class Pair<K, V> {
        private final K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public V getValue() {
            return this.value;
        }

        public K getKey() {
            return this.key;
        }
    }

    public static class Bucket {
        private final List<Pair> list;

        public Bucket() {
            this.list = new LinkedList<>();
        }

        public void addPair(Pair p) {
            this.list.add(p);
        }

        public void removePair(Pair p) {
            this.list.remove(p);
        }

        public List<Pair> getList() {
            return this.list;
        }
    }

    public static class MyHashMap<K, V> {
        // put
        // поиск по ключу
        // удаление по ключу

        private int capacity = 10;
        private Bucket[] buckets;
        private int size;

        public MyHashMap() {
            this.buckets = new Bucket[this.capacity];
        }

        private int hash(K key) {
            return Math.abs(key.hashCode()) % this.capacity;
        }

        public V put(K key, V value) {
            if(this.containsKey(key)) {
                Pair<K, V> pair = this.getPair(key);
                if(pair != null) {
                    pair.setValue(value);
                    return pair.getValue();
                } else {
                    return null;
                }
            }
            int hash = this.hash(key);
            if(this.buckets[hash] == null) {
                this.buckets[hash] = new Bucket();
            }

            this.buckets[hash].addPair(new Pair<>(key, value));
            this.size++;
            return null;
        }

        public V get(K key) {
            Pair<K, V> p = this.getPair(key);
            if(p == null) {
                return null;
            }
            return p.getValue();
        }

        private Pair<K, V> getPair(K key) {
            int hash = this.hash(key);
            if(this.buckets[hash] == null) {
                return null;
            }
            for(Pair<K, V> pair : this.buckets[hash].getList()) {
                if(pair.getKey().equals(key)) {
                    return pair;
                }
            }
            return null;
        }

        public boolean containsKey(K key) {
            return this.getPair(key) != null;
        }

        public V remove(K k) {
            if(this.containsKey(k)) {
                int hash = this.hash(k);
                Pair<K, V> pair = this.getPair(k);
                if(pair != null) {
                    this.buckets[hash].removePair(pair);
                    return pair.getValue();
                } else {
                    return null;
                }
            }
            return null;
        }

        public void printMap() {
            System.out.println("MyHashMap {");
            for(int i = 0; i < this.buckets.length; i++) {
                Bucket b = this.buckets[i];
                if(b != null) {
                    System.out.println("    Bucket #" + i + " {");
                    b.getList().forEach(pair -> {
                        System.out.println("        " + pair.getKey() + "=" + pair.getValue());
                    });
                    System.out.println("    }");
                    System.out.println();
                }
            }
            System.out.println("}");
        }
    }

    public static class MyHashSet<E> {
        private static final Object PRESENT = new Object();

        private final MyHashMap<E, Object> map;

        public MyHashSet() {
            this.map = new MyHashMap<>();
        }

        public boolean add(E element) {
            return this.map.put(element, PRESENT) == null;
        }

        public boolean remove(E key) {
            return this.map.remove(key) == PRESENT;
        }

        public void printSet() {
            List<Object> list = new ArrayList<>();
            for(Bucket b : this.map.buckets) {
                if(b != null) {
                    for(Pair p : b.getList()) {
                        list.add(p.key);
                    }
                }
            }
            System.out.println(list);
        }
    }

    public static void main(String[] args) {
        // System.out.println("Hello, World!");

        MyHashSet<Integer> ints = new MyHashSet<>();
        ints.add(58);
        ints.add(693);
        ints.add(165);
        ints.add(564654);
        ints.add(54646);

        ints.printSet();
        System.out.println("-");

        ints.remove(123);
        ints.remove(56);

        ints.printSet();
        System.out.println("-");

        ints.add(6666);

        ints.printSet();
    }
}
