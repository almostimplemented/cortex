(ns cortex.util
  (:require
    [clojure.core.matrix :as mat])
  (:import [java.util Random]))

(defn timestamp [] (System/nanoTime))

(defn ms-elapsed
  ([start]
   (ms-elapsed start (timestamp)))
  ([start end]
   (/ (double (- end start)) 1000000.0)))

; Functions and Hyperbolic functions 
(defn exp
  [a]
  (mat/emap #(Math/exp %) a))

(defn exp!
  [a]
  (mat/emap! #(Math/exp %) a))

(defn log
  [a]
  (mat/emap #(Math/log %) a))

(defn log!
  [a]
  (mat/emap! #(Math/log %) a))

(defn tanh
  [a]
  (mat/emap #(Math/tanh %) a))

(defn tanh!
  [a]
  (mat/emap! #(Math/tanh %) a))

(defn tanh'
  " tanh'(x) = 1 - tanh(x)^2 "
  [a]
  (math/emap (- 1 (* a a))))

(defn sigmoid
  "y =  1 / (1 + e^(-z))
  Produces an output between 0 and 1."
  [z]
  (mat/div! (mat/add! (util/exp! (mat/negate z)) 1.0)))

(defn sigmoid!
  "y =  1 / (1 + e^(-z))
  It is a smooth threshold function with output between 0 and 1."
  [z]
  (mat/div! (mat/add! (util/exp! (mat/negate! z)) 1.0)))

(defn sigmoid'
  "sigma'(x) = sigma(x) * (1-sigma(x)) "
  [z]
  (let [sz (sigmoid z)]
    (mat/emul sz (mat/sub 1.0 sz))))

(defn rand-vector
  "Produce a vector with guassian random elements having mean of 0.0 and std of 1.0."
  [n]
  (let [rgen (Random.)]
    (mat/array (repeatedly n #(.nextGaussian rgen)))))

(defn rand-matrix
  [m n]
  (let [rgen (Random.)]
    (mat/array (repeatedly m (fn [] (repeatedly n #(.nextGaussian rgen)))))))

(defn weight-matrix
  [m n]
  (let [stdev (/ 1.0 n)]
    (mat/emap #(* stdev %) (rand-matrix m n))))
