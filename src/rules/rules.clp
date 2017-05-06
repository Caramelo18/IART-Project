(import nrc.fuzzy.jess.*)
(import nrc.fuzzy.*)

(printout t "OLA X" crlf)

(deftemplate temperature (slot celsius))
(deftemplate timeDay (slot hours))

(defrule hotInside
    (temperature {celsius >= 20})
    (timeDay { hours < 18 && hours > 7})
    => (printout t "Close windows" crlf))

(assert (temperature (celsius 30)))
(assert (timeDay (hours 12)))

(run)