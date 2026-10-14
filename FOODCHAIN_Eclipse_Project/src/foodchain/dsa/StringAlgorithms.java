package foodchain.dsa;

import java.util.*;

public class StringAlgorithms {
    private StringAlgorithms() {}

    // KMP: returns all starting positions of pattern in text.
    public static List<Integer> kmpSearch(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        if (pattern == null || pattern.isEmpty() || text == null) return result;
        int[] lps = new int[pattern.length()];
        for (int i = 1, len = 0; i < pattern.length();) {
            if (pattern.charAt(i) == pattern.charAt(len)) lps[i++] = ++len;
            else if (len != 0) len = lps[len - 1];
            else lps[i++] = 0;
        }
        for (int i = 0, j = 0; i < text.length();) {
            if (text.charAt(i) == pattern.charAt(j)) { i++; j++; }
            if (j == pattern.length()) { result.add(i - j); j = lps[j - 1]; }
            else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) j = lps[j - 1]; else i++;
            }
        }
        return result;
    }

    // Z-function: Z[i] is the matching prefix length starting at i.
    public static int[] zFunction(String s) {
        int n = s.length();
        int[] z = new int[n];
        for (int i = 1, l = 0, r = 0; i < n; i++) {
            if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
            if (i + z[i] - 1 > r) { l = i; r = i + z[i] - 1; }
        }
        return z;
    }

    // Rabin-Karp substring search using a rolling hash.
    public static List<Integer> rabinKarp(String text, String pattern) {
        List<Integer> hits = new ArrayList<>();
        if (pattern == null || pattern.isEmpty() || text == null || pattern.length() > text.length()) return hits;
        long mod = 1_000_000_007L, base = 257L, ph = 0, th = 0, power = 1;
        int m = pattern.length();
        for (int i = 0; i < m; i++) {
            ph = (ph * base + pattern.charAt(i)) % mod;
            th = (th * base + text.charAt(i)) % mod;
            if (i < m - 1) power = (power * base) % mod;
        }
        for (int i = 0; i <= text.length() - m; i++) {
            if (ph == th && text.regionMatches(i, pattern, 0, m)) hits.add(i);
            if (i < text.length() - m) {
                th = (th - text.charAt(i) * power % mod + mod) % mod;
                th = (th * base + text.charAt(i + m)) % mod;
            }
        }
        return hits;
    }

    // Levenshtein edit distance: insertions, deletions, substitutions.
    public static int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost);
            }
        }
        return dp[a.length()][b.length()];
    }

    // Damerau-Levenshtein distance, including adjacent character transposition.
    public static int damerauLevenshtein(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] d = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) d[i][0] = i;
        for (int j = 0; j <= m; j++) d[0][j] = j;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1),
                        d[i - 1][j - 1] + cost);
                if (i > 1 && j > 1 && a.charAt(i - 1) == b.charAt(j - 2)
                        && a.charAt(i - 2) == b.charAt(j - 1)) {
                    d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + 1);
                }
            }
        }
        return d[n][m];
    }

    // Suffix array: simple educational implementation (sorting all suffixes).
    public static int[] suffixArray(String text) {
        Integer[] order = new Integer[text.length()];
        for (int i = 0; i < order.length; i++) order[i] = i;
        Arrays.sort(order, (x, y) -> text.substring(x).compareTo(text.substring(y)));
        int[] result = new int[order.length];
        for (int i = 0; i < order.length; i++) result[i] = order[i];
        return result;
    }

    // LCP values between adjacent suffixes in suffix-array order (direct comparison).
    public static int[] lcpArray(String text, int[] sa) {
        if (sa.length < 2) return new int[0];
        int[] lcp = new int[sa.length - 1];
        for (int i = 0; i < lcp.length; i++) {
            int a = sa[i], b = sa[i + 1], k = 0;
            while (a + k < text.length() && b + k < text.length()
                    && text.charAt(a + k) == text.charAt(b + k)) k++;
            lcp[i] = k;
        }
        return lcp;
    }
}
