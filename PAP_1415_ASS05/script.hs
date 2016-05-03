data BSTree a = Nil | Node a (BSTree a) (BSTree a)

print_nodes_at_dist :: BSTree Int -> Int -> [Int]
print_nodes_at_dist Nil _ = []
print_nodes_at_dist (Node value l r) d
	| d == 0 = value:[]
	| otherwise = (print_nodes_at_dist l (d - 1)) ++ (print_nodes_at_dist r (d - 1))

testBS :: BSTree Int
testBS = Node 0 (Node 1 (Node 2 (Node 3 (Node 4 Nil Nil) (Node 5 Nil Nil)) Nil) Nil) (Node 6 Nil Nil)