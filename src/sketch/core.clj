(ns sketch.core
  (:require [quil.core :as q]
            [sketch.dynamic :as dynamic])
  (:gen-class))

(q/defsketch sketch
             :title "Seethe"
             :setup dynamic/initialise
             :draw dynamic/draw
             :features [:keep-on-top]
             :size [900 900])

(defn refresh
  "Refresh the sketch."
  []
  (use :reload 'sketch.dynamic)
  (.loop sketch))
