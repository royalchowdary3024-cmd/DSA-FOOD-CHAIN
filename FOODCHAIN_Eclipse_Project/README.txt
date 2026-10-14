FOODCHAIN - Eclipse Java DSA Project
===================================

WHAT THIS PROJECT IMPLEMENTS
- Console menu application
- Product catalogue and inventory stock management
- Customer registration
- Order placement with stock validation and automatic stock deduction
- Product search using KMP and typo-tolerant Levenshtein distance
- KMP, Z-function, Rabin-Karp, Levenshtein and Damerau-Levenshtein demonstrations
- Suffix array and LCP demonstration (educational straightforward implementation)
- Distribution network maximum flow using Edmonds-Karp
- Supplier-to-warehouse bipartite matching

IMPORTANT SCOPE NOTE
This is a console-based educational implementation aligned with the supplied PPT.
It stores data in memory, so data resets when the application exits. It is not yet
a database-backed production system. The PPT also names Aho-Corasick, suffix tree,
suffix automaton, Bitmask DP, Matrix Chain, Optimal BST, Ford-Fulkerson and Dinic;
these are not implemented in this starter version.

HOW TO IMPORT INTO ECLIPSE
1. Extract FOODCHAIN_Eclipse_Project.zip.
2. Open Eclipse.
3. Select File > New > Java Project.
4. Project name: FOODCHAIN.
5. If Eclipse asks to create module-info.java, choose not to create it.
6. In the new project, create the packages shown in the extracted src folder:
   foodchain, foodchain.model, foodchain.service, foodchain.dsa.
7. Copy the .java files into their matching packages, or use File > Import >
   General > File System and import the extracted src folder into the project.
8. Open src/foodchain/FoodChainApp.java.
9. Right-click > Run As > Java Application.

RECOMMENDED TEST
- Choose 1 to see sample products.
- Choose 3 and search for "Basamati" to test typo-tolerant search for Basmati Rice.
- Choose 7, use customer ID 1, product ID 1, quantity 2.
- Choose 1 again to confirm stock reduced by 2.
- Choose 9 to demonstrate string algorithms.
- Choose 10 to demonstrate max-flow and bipartite matching.
- Choose 0 to exit.

JAVA VERSION
Java 8 or later.
