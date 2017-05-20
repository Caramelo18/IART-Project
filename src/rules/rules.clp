(import nrc.fuzzy.jess.*)
(import nrc.fuzzy.*)

(defglobal ?*dayTempMin* = 20)
(defglobal ?*dayTempMax* = 22)
(defglobal ?*nightTempMin* = 14.5)
(defglobal ?*nightTempMax* = 17)
(defglobal ?*timeMin* = 7)
(defglobal ?*timeMax* = 18)
(defglobal ?*wind* = 50)
(defglobal ?*pHMin* = 5.5)
(defglobal ?*pHMax* = 6.5)
(defglobal ?*soilMin* = 40)
(defglobal ?*soilMax* = 60)
(defglobal ?*airMin* = 60)
(defglobal ?*airMax* = 80)

/*
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
*/

(deffunction params (?vdayTempMin ?vdayTempMax ?vnightTempMin ?vnightTempMax ?vtimeMin ?vtimeMax ?vwind ?vpHMin ?vpHMax ?vsoilMin ?vsoilMax ?vairMin ?vairMax)
    (bind ?*dayTempMin* ?vdayTempMin)
    (bind ?*dayTempMax* ?vdayTempMax)
    (bind ?*nightTempMin* ?vnightTempMin)
    (bind ?*nightTempMax* ?vnightTempMax)
    (bind ?*timeMin* ?vtimeMin)
    (bind ?*timeMax* ?vtimeMax)
    (bind ?*wind* ?vwind)
    (bind ?*pHMin* ?vpHMin)
    (bind ?*pHMax* ?vpHMax)
    (bind ?*soilMin* ?vsoilMin)
    (bind ?*soilMax* ?vsoilMax)
    (bind ?*airMin* ?vairMin)
    (bind ?*airMax* ?vairMax)
    (printout t "New configuration is in effect" crlf)
)


(deftemplate temperature (slot celsius))
(deftemplate timeDay (slot hours))
(deftemplate phWater (slot ph))
(deftemplate windSpeed (slot velocity))
(deftemplate airHumidity (slot percentage))
(deftemplate soilHumidity (slot percentage))
(deftemplate conductivity (slot water))

(defrule hotInside
    (or (and (temperature {celsius >= ?*dayTempMax*})
            (timeDay { hours < ?*timeMax* && hours > ?*timeMin*}))
        (and (temperature {celsius >= ?*nightTempMax*})
        (timeDay { hours > ?*timeMax* || hours < ?*timeMin*})))
    => (printout t "Open the windows. It's hot inside." crlf))

(defrule coldInside
    (or (and (temperature {celsius < ?*dayTempMin*})
            (timeDay { hours < ?*timeMax* && hours > ?*timeMin*}))
        (and (temperature {celsius < ?*nightTempMin*})
            (timeDay { hours > ?*timeMax* || hours < ?*timeMin*})))
    => (printout t "Close the windows. It's cold inside." crlf))

(defrule windy
    (windSpeed {velocity >= ?*wind*})
    => (printout t "Windy. Please close the windows." crlf))

(defrule wetAir
    (airHumidity {percentage > ?*airMax*})
    => (printout t "Turn the heating on." crlf))

(defrule dryAir
    (airHumidity {percentage < ?*airMin*})
    => (printout t "Turn the heating off." crlf))

(defrule lowPh
    (phWater {ph < ?*pHMin*})
    => (printout t "Add alkaline to the water." crlf))

(defrule highPh
    (phWater {ph > ?*pHMax*})
    => (printout t "Add acidity to the water." crlf))

(defrule wetSoil
    (soilHumidity {percentage > ?*soilMax*})
    => (printout t "Decrease irrigation duration." crlf))

(defrule drySoil
    (soilHumidity {percentage < ?*soilMin*})
    => (printout t "Increase irrigation duration." crlf))

