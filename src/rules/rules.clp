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
(defglobal ?*outTemp* = 25)
(defglobal ?*outAirHum* = 50)
(defglobal ?*outWind* = 10)

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

(deffunction params2 (?voutTemp ?voutAirHum ?voutWind)
    (bind ?*outTemp* ?voutTemp)
    (bind ?*outAirHum* ?voutAirHum)
    (bind ?*outWind* ?voutWind)
    (printout t "Weather updated! " ?*outTemp* " " ?*outAirHum* crlf))

(deftemplate temperature
        (slot celsius))
(deftemplate timeDay
        (slot hours))
(deftemplate phWater
        (slot ph))
(deftemplate windSpeed
        (slot velocity))
(deftemplate airHumidity
        (slot percentage))
(deftemplate soilHumidity
        (slot percentage))
(deftemplate conductivity
        (slot water))

(defrule hotInsideColderOutside
    (and (temperature {celsius > ?*outTemp*})
         (or (and (temperature {celsius >= ?*dayTempMax*})
                  (timeDay { hours < ?*timeMax* && hours > ?*timeMin*}))
             (and (temperature {celsius >= ?*nightTempMax*})
             (timeDay { hours > ?*timeMax* || hours < ?*timeMin*}))))
    => (printout t "It's hot inside and colder outside. Open the windows." crlf))

(defrule hotInsideHotterOutside
    (and (temperature {celsius < ?*outTemp*})
         (or (and (temperature {celsius > ?*dayTempMax*})
                  (timeDay { hours < ?*timeMax* && hours > ?*timeMin*}))
             (and (temperature {celsius >= ?*nightTempMax*})
             (timeDay { hours > ?*timeMax* || hours < ?*timeMin*}))))
    => (printout t "It's hot inside but hotter outside. Keep windows closed and turn on cooling fans. " crlf))

(defrule coldInsideColderOutside
    (and (temperature {celsius > ?*outTemp*})
         (or (and (temperature {celsius < ?*dayTempMin*})
                (timeDay { hours < ?*timeMax* && hours > ?*timeMin*}))
            (and (temperature {celsius < ?*nightTempMin*})
                (timeDay { hours > ?*timeMax* || hours < ?*timeMin*}))))
    => (printout t "It's cold inside and colder outside. Close the windows and turn on the heaters" crlf))

(defrule coldInsideHotterOutside
    (and (temperature {celsius > ?*outTemp*})
         (or (and (temperature {celsius < ?*dayTempMin*})
                (timeDay { hours < ?*timeMax* && hours > ?*timeMin*}))
            (and (temperature {celsius < ?*nightTempMin*})
                (timeDay { hours > ?*timeMax* || hours < ?*timeMin*}))))
    => (printout t "It's cold inside but hotter outside. Open the windows" crlf))

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

(defrule lowWaterCond
    (conductivity {water < 2})
    => (printout t "Water's conductivity too low. Increase nutrients in irrigation water." crlf))

(defrule highWaterCond
    (conductivity {water > 3})
    => (printout t "Water's conductivity too high. Decrease nutrients in irrigation water." crlf))
