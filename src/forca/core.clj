(ns forca.core)

;; declarando a função, para que, no momento da compilação, ele
;; saiba que existe uma "avalia-chute", que é invocada pela jogo
(declare avalia-chute)

;; definindo o total de vidas que nosso jogo tem, e a palavra secreta
(def total-de-vidas 6)
(def palavra-secreta "MELANCIA")

(defn perdeu []
	(print "Você perdeu, mané!"))

(defn ganhou []
	(print "Caraca, parabéns!"))

;; .contains invoca funcao do java
;; ? no fim, é pq retorna booleano
(defn acertou? [chute palavra]
	(.contains palavra chute))

;; ! é pq tem side-effect
(defn le-letra! []
	(read-line))

;; (str) pra converter Char pra String, pq nosso conjunto é de strings
;; fn declarando uma função
;; seq quebra uma string em uma lista de chars
(defn letras-faltantes [palavra acertos]
	(filter (fn [letra] (not (contains? acertos (str letra)))) (seq palavra)))

(defn acertou-a-palavra-toda? [palavra acertos]
	(empty? (letras-faltantes palavra acertos)))

;; if dentro de if
(defn jogo [vidas palavra acertos]
	(if (= vidas 0) (perdeu) 
		(if (acertou-a-palavra-toda? palavra acertos) 
			(ganhou) 
			(avalia-chute (le-letra!) vidas palavra acertos))))

;; conj adiciona no conjunto
;; dec diminui 1 na variavel
(defn avalia-chute [chute vidas palavra acertos]
	(if (acertou? chute palavra) 
		(jogo vidas palavra (conj acertos chute))
		(jogo (dec vidas) palavra acertos)))

;; comecamos o jogo com o total de vidas, a palavra secreta, e nada de acertos
;; no repl
;; (require '[forca.core :as forca] :reload)
;; (forca/comeca-o-jogo) pra jogar
(defn comeca-o-jogo []
	(jogo total-de-vidas palavra-secreta #{}))
