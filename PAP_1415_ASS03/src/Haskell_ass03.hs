import Data.List

data StarSeq = Star StarSeq | End deriving (Show)

test1 = Star (Star (Star (End)))
vec1 = [Star (Star (Star (End))), Star (Star (End)), Star (Star (Star (Star (Star (End))))), Star (Star (Star (End))), Star (Star (Star (End)))]

count :: StarSeq -> Int
count End = 0
count (Star st) = 1 + count st

getPos :: [StarSeq] -> Int -> Int -> Int
getPos [] _ _ = 0
getPos (s:sx) pos nStar 
	| nStar == count s = pos
	| otherwise = getPos sx (pos+1) nStar

getMaxSeq :: [StarSeq] -> (Int,Int)
getMaxSeq [] = (0, 0)
getMaxSeq ss = foldr (\s (max, pos) -> if(count s > max) then (count s, getPos ss 0 (count s)) else (max, pos)) (0, 0) ss

printSeq :: StarSeq -> String
printSeq End = ""
printSeq (Star xs) = '*' : printSeq xs

comparVectors :: StarSeq -> StarSeq -> Ordering
comparVectors a b
	| (count a) >= (count b) = GT
	| otherwise = LT

printSeqs :: [StarSeq] -> IO ()
printSeqs ss = foldr (>>) (return ()) (map (\x -> putStrLn (printSeq x)) (sortBy comparVectors ss))