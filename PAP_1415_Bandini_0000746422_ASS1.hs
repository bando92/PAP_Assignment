data Elem = Dot | Star
data BSTree a = Nil | Node a (BSTree a) (BSTree a)

---------------- Dichiarazione test ---------------
vec :: [Elem]
vec = [Star, Star, Star, Star, Dot, Dot, Dot, Dot, Star, Star, Dot, Dot, Star, Star, Star, Dot, Star, Star, Dot]

testTree :: BSTree Elem
testTree = (Node Star
				(Node Star
					(Node Star 
						(Node Dot Nil Nil)
					Nil)
					(Node Star Nil Nil))
				(Node Star
					(Node Star 
						(Node Star Nil Nil)
					Nil)
					Nil))

--countStar
countStar :: [Elem] -> Int
countStar [] = 0
countStar (Star:xs) = 1 + countStar xs
countStar (Dot:xs) = countStar xs

--printableSeq
printableSeq :: [Elem] -> [Char]
printableSeq [] = ""
printableSeq (Star:xs) = '*' : printableSeq xs
printableSeq (Dot:xs) = '.' : printableSeq xs

--swapSeq
swapSeq :: [Elem] -> [Elem]
swapSeq [] = []
swapSeq (Star:xs) = Dot : swapSeq xs
swapSeq (Dot:xs) = Star : swapSeq xs

--zipSeq
zipSeq :: [Elem] -> [Elem]
zipSeq [] = []
zipSeq (Star:xs) = Star : zipSeq xs --se ho uno Star seguito da una qualsiasi cosa ritorno uno Star e il risultato calcolato sulla restante lista
zipSeq (Dot:(Dot:xs)) = zipSeq (Dot:xs) --se c'è un Dot seguito da un'altro Dot ritorno un Dot concatenato al risultato calcolato sulla restante parte della lista
zipSeq (Dot:(Star:xs)) = Dot:(Star:zipSeq xs) --se c'è un Dot seguito da uno Star ritorno un Dot seguito da uno Star e dal restante risultato
zipSeq (Dot:[]) = [Dot] --se arrivo in fondo avendo un Dot lo ritorno come unico valore 

--maxStarSeq
--nextStarSeq è una funzione che calcola la lunghezza della prossima sequenza di Star
nextStarSeq :: [Elem] -> Int
nextStarSeq [] = 0 --caso base
nextStarSeq (Star:Dot:xs) = 1 --se ho uno star seguito da un dot(sono alla fine della sequenza) ritorno 1
nextStarSeq (Dot:xs) = nextStarSeq  xs --se ho un dot non faccio nulla e passo alla parte successiva della lista
nextStarSeq (Star:xs) = 1 + nextStarSeq  xs --se ho una star aggiungo 1 al passo ricorsivo

maxStarSeq :: [Elem] -> Int
maxStarSeq [] = 0
maxStarSeq (x:xs) 
	| nextStarLength > maxS 	= nextStarLength
	| otherwise 				= maxS
	where
		nextStarLength = nextStarSeq  (x:xs)
		maxS = maxStarSeq xs

--matchSeq
matchSeq :: [Elem] -> [Elem] -> Bool
matchSeq [] [] = True --caso base
matchSeq [] (Star:ys) = False
matchSeq (Star:xs) [] = False
matchSeq (Dot:xs) y = matchSeq xs y --se trovo un dot nella prima lista lo ignoro e vado avanti nella ricerca
matchSeq x (Dot:ys) = matchSeq x ys --se trovo un dot nella seconda lista lo ignoro e vado avanti nella ricerca
matchSeq (Star:xs) (Star:ys) = matchSeq xs ys --se ho star in entrambe le liste va bene e continuo la ricorsione sulle restanti parti delle liste
matchSeq (Star:(Dot:xs)) (Star:(Star:ys)) = False -- usando un specie di look-ahead controllo i valori dopo le star...se sono diversi il risultato è false
matchSeq (Star:(Star:xs)) (Star:(Dot:ys)) = False


--occ

--prende una lista che inizia con dot e quando trova la prima star, guardo quant'è grande il cluster con NextStarSeq e ritorna la lista di posizioni d'inizio dei cluster di occur
getClusterPositions :: Int -> Int -> [Elem] -> [Int]
getClusterPositions _ _ [] = []
--occur contiene il risultato di NextStarSeq -> dimensione della prossima sequenza di star
getClusterPositions occur pos (Dot:Star:xs)
										--ritorna una posizione concatenata con la ricorsione sulle restanti posizioni
	| occur == nextStarSeq (Star:xs) = (pos+1) : getClusterPositions occur (pos+2) xs
	| otherwise = getClusterPositions occur (pos+2) xs
getClusterPositions occur pos (_:xs) = getClusterPositions occur (pos+1) xs

--trasforma le stelle in dot solo nei cluster che sono grandi occur
removeCluster :: Int -> Int -> [Elem] -> [Elem]
removeCluster _ _ [] = []
removeCluster occur _ (Dot:xs) = Dot:removeCluster occur 0 xs
removeCluster occur index (Star:xs)
	| (index + nextStarSeq (Star:xs)) == occur = Dot:(removeCluster occur (index+1) xs)
	| otherwise = Star:(removeCluster occur (index+1) xs)

occ :: [Elem] -> [(Int, [Int])]
occ x
	--se ci sono ancora star => occ = (grandezza del cluster(nextStarSeq x), [posizioni dei cluster così grandi] (getClusterPositions)) concatenato con la ricorsione in cui gli ripasso la lista senza i cluster così grandi
	| countStar x > 0 = (nextStarSeq x, getClusterPositions (nextStarSeq x) 0 (Dot:x)) : occ (removeCluster (nextStarSeq x) 0 x)
	| otherwise = []

--countInBst
countStarInTree :: BSTree Elem -> Int
countStarInTree Nil = 0
countStarInTree (Node Star sx dx) = 1 + countStarInTree sx + countStarInTree dx
countStarInTree (Node Dot sx dx) = countStarInTree sx + countStarInTree dx

--pathTree
pathTree :: BSTree Elem -> Int
pathTree Nil = 0
pathTree (Node Dot sx dx) = 0
pathTree (Node Star sx dx)
	| pathTree sx > pathTree dx 	= 1 + pathTree sx
	| pathTree sx <= pathTree dx 	= 1 + pathTree dx