import GHC.Float
import Screen

--dichiarazioni tipi e variabili
type P2d = (Int, Int)
type V2d = (Int, Int)

data Shape = Line P2d P2d | Triangle P2d P2d P2d | Rectangle P2d P2d | Circonference P2d Int | Composition [Shape]
data BSTree a = Nil | Node a (BSTree a) (BSTree a)

linea1 :: Shape
linea1 = Line (1,2) (20,30)

triangolo1 :: Shape
triangolo1 = Triangle (10,13) (40,20) (1,36)

rettangolo1 :: Shape
rettangolo1 = Rectangle (19,19) (35,35)

comp1 :: [Shape]
comp1 = [linea1, rettangolo1, triangolo1]
--funzioni ausiliarie

distance :: P2d -> P2d -> Float --calcola la distanza tra 2 punti
distance (ax, ay) (bx, by) = sqrt (int2Float((bx - ax)^2) + int2Float((by - ay)^2))

add :: P2d -> V2d -> P2d --sposta un punto (px,py) lungo il vettore (vx,vy)
add (px, py) (vx, vy) = ((px+vx), (py+vy))

pointInP1 :: P2d -> P2d -> Bool
pointInP1 (ax,ay) (p1x,p1y )= (ax >= p1x) && (ay <= p1y)
pointInP2 :: P2d -> P2d -> Bool
pointInP2 (ax,ay) (p2x,p2y) = (ax <= p2x) && (ay >= p2y)
outer :: P2d -> P2d -> Bool
outer (px, py) (boundx, boundy) = (px >= boundx) && (py >= boundy)
inner :: P2d -> P2d -> Bool
inner (px, py) (boundx, boundy) = (px <= boundx) && (py <= boundy)

lesserx :: Shape -> Shape -> Bool
lesserx s1 s2
	| getXpos s1 < getXpos s2 	= True
	| otherwise 				= False

pair :: [a] -> [b] -> [(a,b)]
pair [] [] = []
pair [] _ = error "different lengths... cannot pair"
pair _ [] = error "different lengths... cannot pair"
pair (x:xs) (y:ys) = (x,y):(pair xs ys)

deg2rad :: Int -> Float
deg2rad deg = (int2Float deg) * (pi / 180.0)

myBoolPrint :: Bool -> IO ()
myBoolPrint a = if a == True
			then putStrLn "true"
			else putStrLn "false"
--classe CShape

class CShape a where
	perim :: a -> Float
	move :: a -> V2d -> Shape
	area :: a -> Float
	getXpos :: a -> Int

instance CShape Shape where
	perim (Line a b) = distance a b
	perim (Triangle a b c) = distance a b + distance a c + distance b c
	perim (Rectangle (ax,ay) (bx,by)) = 2 * abs (int2Float ax - int2Float bx) + 2 * abs (int2Float ay - int2Float by)
	perim (Circonference _ radius) = 2 * pi * int2Float radius
	perim (Composition l) = foldr (\x sum -> perim x + sum) 0.0 l

	move (Line a b) v = (Line (add a v) (add b v))
	move (Triangle a b c) v = (Triangle (add a v) (add b v) (add c v))
	move (Rectangle a b) v = (Rectangle (add a v) (add b v))
	move (Circonference center radius) v = (Circonference (add center v) radius)
	move (Composition list) v = (Composition (map (\x -> move x v) list))

	-- aree utilizzate nella funzione maxArea
	area (Line _ _) = 0.0
	area (Triangle a b c) = sqrt ( -- formula di erone
		per * (per - distance a b) * (per - distance b c) * (per - distance a c))
		where per = perim (Triangle a b c) / 2
	area (Rectangle (ax,ay) (bx,by)) = 
		base * altezza
		where 
			base  = abs (int2Float ax - int2Float bx)
			altezza = abs (int2Float ay - int2Float by)
	area (Circonference _ radius) = pi * (int2Float (radius^2))
	area (Composition list) = foldr (\x a -> area x + a) 0.0 list

	getXpos (Line (ax, _) (bx, _)) = min ax bx
	getXpos (Triangle (ax, _) (bx, _) (cx, _)) = min ax (min bx cx)
	getXpos (Rectangle (posx, _) (_, _)) = posx
	getXpos (Circonference (posx, _) radius) = posx - radius
	getXpos (Composition list) = foldr (\x m -> min (getXpos x) m) (maxBound :: Int) list
	
--funzioni
moveShapes :: [Shape] -> V2d -> [Shape]
moveShapes list v = map (\x -> move x v) list --con la map applico la funzione move a tutti gli elementi della lista list

--funzione ausilizria che indica se una figura è contenuta nella buonding box restituendo un valore booleano
isInBBox :: Shape -> P2d -> P2d -> Bool
isInBBox (Line a b) p1 p2 = and [pointInP1 a p1, pointInP1 b p1, pointInP2 a p2, pointInP2 b p2]
isInBBox (Triangle a b c) p1 p2 = and [pointInP1 a p1, pointInP1 b p1, pointInP1 c p1, pointInP2 a p2, pointInP2 b p2, pointInP2 c p2]
isInBBox (Rectangle a b) p1 p2 = and [pointInP1 a p1, pointInP1 b p1, pointInP2 a p2, pointInP2 b p2]
isInBBox (Circonference center radius) p1 p2 = and [ --dal centro calcolo i 4 punti aggiungendo e togliendo la lunghezza del raggio sull'asse x e sull'asse y
	pointInP1 (add center (-radius, 0)) p1,
	pointInP1 (add center (0, -radius)) p1,
	pointInP1 (add center (radius, 0)) p1,
	pointInP1 (add center (0, radius)) p1,
	pointInP2 (add center (-radius, 0)) p2,
	pointInP2 (add center (0, -radius)) p2,
	pointInP2 (add center (radius, 0)) p2,
	pointInP2 (add center (0, radius)) p2]
isInBBox (Composition list) p1 p2 = foldr (\x b -> and [isInBBox x p1 p2, b]) True list --per le composition metto tutti i risultati delle varie figure in and e se restituisce true vuol dire che tutte le figure sono dentro la BB

--Utilizzando la funzione sopra filtra la lista passata alla funzione e crea una nuova lista contenente solo le shape dentro la Bounding Box 
inBBox :: [Shape] -> P2d -> P2d -> [Shape]
inBBox l p1 p2 = filter (\x -> isInBBox x p1 p2) l

maxArea :: [Shape] -> Shape
maxArea (x:xs) = foldr (\p s-> if (area p > area s) then p else s) x (x:xs)

insertInTree :: BSTree Shape -> Shape -> BSTree Shape
insertInTree Nil shape = (Node shape Nil Nil)
insertInTree (Node s sx dx) shape
	| lesserx shape s 	= (Node s (insertInTree sx shape) dx)
	| otherwise			= (Node s sx (insertInTree sx shape))

makeShapeTree :: [Shape] -> BSTree Shape
makeShapeTree [] = Nil
makeShapeTree (x:xs) = insertInTree (makeShapeTree xs) x

--draw
linearInterpolation :: Int -> Int -> Int -> [Int] -- start, end, step
linearInterpolation _ end 50 = end:[]
linearInterpolation start end step = 
	float2Int ((int2Float start) + ((int2Float step / 50) * int2Float (end - start)))
	: linearInterpolation start end (step + 1)

interpolateLine :: Shape -> [IO()]
interpolateLine (Line (ax, ay) (bx, by)) = map (\p -> writeAt p "*") (pair (linearInterpolation ax bx 0) (linearInterpolation ay by 0))

drawAll :: [Shape] -> IO()

class (CShape a) => Drawable a where
	draw :: a -> IO()

instance Drawable Shape where
	draw (Line a b) = foldr (>>) (goto (0,50)) (interpolateLine (Line a b))
	draw (Triangle a b c) = do
		draw (Line a b)
		draw (Line b c)
		draw (Line a c)
	draw (Rectangle (ax, ay) (bx, by)) = do
		draw (Line bassosx bassodx)
		draw (Line altosx altodx)
		draw (Line bassosx altosx)
		draw (Line bassodx altodx)
		where
			bassosx = (ax, ay)
			bassodx = (bx, ay)
			altosx = (ax, by)
			altodx = (bx, by)
	draw (Composition list) = drawAll list
	--non sono riuscito ad implementare il disegno del cerchio
drawAll list = foldr (>>) (goto (0,50)) (map (\x -> draw x) list)