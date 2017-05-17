(import nrc.fuzzy.jess.*)
(import nrc.fuzzy.*)


(defglobal ?*fuzzy-temp* = (new nrc.fuzzy.FuzzyVariable "temp" 0.0 40.0 "C"))
(defglobal ?*fuzzy-time* = (new nrc.fuzzy.FuzzyVariable "time" 0.0 24.0 "h"))

(defrule init
    =>
    (load-package nrc.fuzzy.jess.FuzzyFunctions)
    ;; temperature
    (bind ?xHot (create$ 22.0 40.0))
    (bind ?yHot (create$ 0.0 1.0))
    (bind ?xCold (create$ 0.0 20.0))
    (bind ?yCold (create$ 1.0 0.0))
    (bind ?xNice (create$ 20.0 22.0))
    (bind ?yNice (create$ 1.0 1.0))
    (?*fuzzy-temp* addTerm "hot" ?xHot ?yHot 2)
    (?*fuzzy-temp* addTerm "cold" ?xCold ?yCold 2)
    (?*fuzzy-temp* addTerm "nice" ?xNice ?yNice 2)
    (assert (theTemp (new nrc.fuzzy.FuzzyValue ?*fuzzy-temp* "very medium")))

    ;; time of day
    (bind ?xDay (create$ 7.0 19.0))
    (bind ?yDay (create$ 1.0 1.0))
    (bind ?xNight (create$ 0.0 7.0))
    (bind ?yNight (create$ 0.0 1.0))
    (bind ?xNight1 (create$ 19.0 24.0))
    (bind ?yNight1 (create$ 1.0 0.0))
)



(deftemplate temperature (slot celsius))
(deftemplate timeDay (slot hours))
(deftemplate phWater (slot ph))
(deftemplate windSpeed (slot velocity))
(deftemplate airHumidity (slot percentage))
(deftemplate soilHumidity (slot percentage))
(deftemplate conductivity (slot water))

(defrule hotInside
    (temperature {celsius >= 22})
    (timeDay { hours < 18 && hours > 7})
    => (printout t "Open the windows. It's hot inside." crlf))

(defrule coldInside
    (temperature {celsius < 20})
    (timeDay { hours < 18 && hours > 7})
    => (printout t "Close the windows. It's cold inside." crlf))

(defrule windy
    (windSpeed {velocity >= 50})
    => (printout t "Windy. Please close the windows." crlf))

(defrule wetAir
    (airHumidity {percentage > .80})
    => (printout t "Turn the heating on." crlf))

(defrule dryAir
    (airHumidity {percentage < .60})
    => (printout t "Turn the heating off." crlf))

(defrule lowPh
    (phWater {ph < 5.5})
    => (printout t "Add alkaline to the water." crlf))

(defrule highPh
    (phWater {ph > 6.5})
    => (printout t "Add acidity to the water." crlf))

(defrule wetSoil
    (soilHumidity {percentage > .60})
    => (printout t "Decrease irrigation duration." crlf))

(defrule drySoil
    (soilHumidity {percentage < .40})
    => (printout t "Increase irrigation duration." crlf))

