(import nrc.fuzzy.jess.*)
(import nrc.fuzzy.*)

(printout t "OLA X" crlf)

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
    (airHumidity {percentage > .60})
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

