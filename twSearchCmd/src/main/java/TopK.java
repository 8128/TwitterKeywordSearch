import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-12-09 00:39
 */
public class TopK {

    private List<String[]> data;
    private HashSet<String> keywords;
    private int k;
    private Scanner scanner;
    private HashMap<String, HashMap<Integer, Integer>> hm;

    /**
     * Constructor for TopK function
     * In this constructor application will load file and create reverse index
     * @param scanner : the system input
     */
    public TopK(Scanner scanner) {
        this.data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("abcnews-date-text.csv")));
            reader.readLine();
            reader.readLine();
            String line;
            while((line=reader.readLine())!=null){
                String[] item = line.split(",");
                if (item.length > 1) {
                    this.data.add(item[1].split(" "));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.scanner = scanner;
        System.out.println(this.data.size() + " twitters loaded\n");
        System.out.println("Start to create reversed index map");
        long start = System.currentTimeMillis();
        this.hm = new HashMap<>();
        for (int i = 0; i < this.data.size(); i++) {
            String[] strs = this.data.get(i);
            HashMap<String, Integer> temp = new HashMap<>();
            for (String str : strs) {
                temp.put(str, temp.getOrDefault(str, 0) + 1);
            }
            for (String key : temp.keySet()) {
                HashMap<Integer, Integer> tmp = this.hm.getOrDefault(key, new HashMap<>());
                tmp.put(i, tmp.getOrDefault(i, 0) + temp.get(key));
                this.hm.put(key, tmp);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Reverse Index time: " + (end - start) + "ms");
    }

    public void setKeywords(String words) {
        this.keywords = new HashSet<String>();
        String[] strings = words.split(" ");
        this.keywords.addAll(Arrays.asList(strings));
    }

    public void setK(int k) {
        this.k = k;
    }

    public List<String[]> bucketSort() {
        ArrayList<String[]>[] lists = new ArrayList[140];
        for (String[] strs : this.data) {
            int sim = similarity(strs);
            if (lists[sim] == null) {
                lists[sim] = new ArrayList<String[]>();
                lists[sim].add(strs);
            }else {
                if(lists[sim].size() < this.k) {
                    lists[sim].add(strs);
                }
            }
        }
        List<String[]> ans = new ArrayList<>();
        int pivot = 139;
        while (ans.size() < this.k && pivot >= 0) {
            if (lists[pivot] != null) {
                ans.addAll(lists[pivot]);
            }
            pivot--;
        }
        if (ans.size() > this.k) {
            ans = ans.subList(0, this.k);
        }
        return ans;
    }

    public List<String[]> heapSort() {
        PriorityQueue<String[]> pq = new PriorityQueue<>(new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                Integer sim1 = similarity(o1);
                Integer sim2 = similarity(o2);
                return sim1.compareTo(sim2);
            }
        });
        for (String[] strs : this.data) {
            pq.offer(strs);
            if (pq.size() > this.k) {
                pq.poll();
            }
        }
        List<String[]> ans = new ArrayList<>();
        while (!pq.isEmpty()) {
            ans.add(pq.poll());
        }
        Collections.reverse(ans);
        return ans;
    }

    public List<Integer> withRIndex() {
        HashMap<Integer, Integer> temp = new HashMap<>();
        for (String str : this.keywords) {
            if (this.hm.containsKey(str)) {
                HashMap<Integer, Integer> insideMap = this.hm.get(str);
                for (int key : insideMap.keySet()) {
                    temp.put(key, temp.getOrDefault(key, 0) + insideMap.get(key));
                }
            }
        }
        List<Integer> ans = new ArrayList<>(temp.keySet());
        Collections.sort(ans, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(temp.get(o2), temp.get(o1));
            }
        });
        if (ans.size() > this.k) {
            ans = ans.subList(0, this.k);
        }
        return ans;
    }

    public int similarity(String[] strs) {
        int sim = 0;
        for (String str : strs) {
            if (this.keywords.contains(str)) {
                sim++;
            }
        }
        return sim;
    }

    /**
     * to print the String[] to String
     * @param strs
     * @return
     */
    public String toString(String[] strs) {
        StringBuilder sb = new StringBuilder();
        for (String string : strs) {
            sb.append(string).append(" ");
        }
        return sb.toString();
    }

    public void sys() {
        System.out.println("\nPlease Input Your Keywords.");
        System.out.println("Split them using spaces, and end with enter");
        String keywords = this.scanner.nextLine();
        setKeywords(keywords);
        System.out.println("Please enter the size of the result:");
        int k = Integer.valueOf(this.scanner.nextLine());
        setK(k);
        System.out.println("\nUsing the bucket sort...");
        long start = System.currentTimeMillis();
        List<String[]> ans = bucketSort();
        long end = System.currentTimeMillis();
        System.out.println("The time cost is " + (end - start) + " ms");
        System.out.println("The answer generated is:\n");
        for (String[] strs : ans) {
            System.out.println(toString(strs));
        }
        System.out.println("\n \n \nUsing the heap sort...");
        start = System.currentTimeMillis();
        ans = heapSort();
        end = System.currentTimeMillis();
        System.out.println("The time cost is " + (end - start) + " ms");
        System.out.println("The answer generated is:\n");
        for (String[] strs : ans) {
            System.out.println(toString(strs));
        }
        System.out.println("\n \n \nUsing the reverse index...");
        List<Integer> reverseAns = new ArrayList<>();
        start = System.currentTimeMillis();
        reverseAns = withRIndex();
        end = System.currentTimeMillis();
        System.out.println("The time cost is " + (end - start) + " ms");
        System.out.println("The answer generated is:\n");
        for (int index : reverseAns) {
            System.out.println(toString(data.get(index)));
        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|                        Twitter Keyword Searcher                              |");
        System.out.println("|                        Developed by Tianyi Tang                              |");
        System.out.println("|                                                                              |");
        System.out.println("|             return the top relevant twitter with your keywords               |");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("Loading CSV File");
        TopK topK = new TopK(scanner);
        topK.sys();
        while (true) {
            System.out.println("\nIf you want to test again, enter 1.\nEnter anything else will exit the program.");
            String str = scanner.nextLine();
            if (str.equals("1")) {
                topK.sys();
            } else {
                break;
            }
        }
        scanner.close();
    }
}
