(ns sketch.dynamic
  (:require [clojure.pprint :as pp]
            [quil.core :as q]))

(def radius 239)

(defn save-frame-to-disk
  ([]
   (q/save-frame (pp/cl-format nil
                               "frames/~d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-####.jpeg"
                               (q/year) (q/month) (q/day)
                               (q/hour) (q/minute) (q/seconds))))
  ([state _]
   (save-frame-to-disk)
   state))

(defn- point-in-circle
  []
  (let [r (* radius
             (q/random-gaussian))
        t (q/random 0
                    (* 2 q/PI))]
    [(* r
        (q/cos t))
     (* r
        (q/sin t))]))

(defn- hue
  []
  (mod (q/random 333 373)
       360))

(defn- draw-curve
  []
  (q/stroke-weight (q/random 1 4))
  (q/stroke-cap :round)
  (q/stroke (hue)
            (q/random 50 100)
            (q/random 100)
            (q/random 1))
  (apply q/curve
         (flatten (take 4
                        (repeatedly point-in-circle)))))

(defn- draw-curves
  [n]
  (dotimes [_ n]
    (draw-curve)))

(defn- draw-seething-mass
  []
  (q/with-translation [(* (q/width)
                          0.83)
                       (* (q/height)
                          0.83)]
    (draw-curves 666)))

(defn- draw-line
  []
  (q/stroke 0 0 (q/random 19 45) (q/random 0.25))
  (q/stroke-cap :project)
  (q/stroke-weight (q/random 250 750))
  (q/line (q/random (q/width)) (q/random (q/height))
          (q/random (q/width)) (q/random (q/height))))

(defn- draw-lines
  [n]
  (dotimes [_ n]
    (draw-line)))

(defn- draw-background
  []
  (draw-lines 29))

(defn draw
  []
  (q/no-loop)
  (q/background 0 0 0)
  (q/no-fill)
  (draw-background)
  (draw-seething-mass)
  (save-frame-to-disk))

(defn initialise
  []
  (q/smooth)
  (q/color-mode :hsb 360 100 100 1.0))