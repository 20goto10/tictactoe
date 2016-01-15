(ns tictactoe.core)

(def width 4)
(def pieces {:blank " ", :ex "X", :oh "O"})
(def piece_count (count (keys pieces)))
(defn boardline [] (vec (repeatedly width #((vec (keys pieces)) (rand-int piece_count)))))
(def board (to-array-2d (vec (repeatedly width #(boardline)))))

(defn- check_consecutives
  "Determine if there is a winner"
  [piece]
  (or 
    (boolean (some true? (for [x (range 0 width)]
      (or 
        (every? #(= piece %) (for [y (range 0 width)] (aget board y x)))
        (every? #(= piece %) (for [y (range 0 width)] (aget board x y)))
      ))
      ))
    (every? #(= piece %) (for [y (range 0 width)] (aget board y y )))
    (every? #(= piece %) (for [y (range 0 width)] (aget board (- width 1 y) y)))
  )  
)

(defn- printboard
  "Print the tictactoe board"
  [&]
  (dotimes [x width] 
    (dotimes [y width] 
      (print (str " " (pieces (aget board x y)) " " 
        (cond 
          (and (= x (- width 1)) (= y (- width 1))) "\n"
          (= (- width 1) (mod y width)) (str "\n─" (apply str (repeat (- width 1) "──┼─")) "──\n")
          :else "│")
        )
      )
    )
  )
)

(defn -main [& args]
  (printboard)
  (println "O: " (check_consecutives :oh))
  (println "X: " (check_consecutives :ex))
  (println "_: " (check_consecutives :blank))
)
