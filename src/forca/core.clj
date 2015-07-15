; lein new app forca
(ns forca.core (:gen-class))

;; definindo o total de vidas que nosso jogo tem, e a palavra secreta
(def total-de-vidas 6)
(def palavra-secreta "MELANCIA")

(defn perdeu []
	(println "Você perdeu, mané!"))

(defn ganhou []
	(println "Caraca, parabéns!"))

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
	(remove (fn [letra] (contains? acertos (str letra))) (seq palavra))
)

(defn acertou-a-palavra-toda? [palavra acertos]
	(empty? (letras-faltantes palavra acertos)))


(defn imprime-forca [vidas palavra acertos]
	(println "Vidas " vidas)
	(map (fn [letra] 
		(if (contains? acertos (str letra)) 
			(println letra " ") 
			(println "_" " ")) 
		) (seq palavra)))

;; if dentro de if => cond
(defn jogo [vidas palavra acertos]
	(imprime-forca vidas palavra acertos)
	(cond
		(= vidas 0) (perdeu) 
		(acertou-a-palavra-toda? palavra acertos) (ganhou) 
		:else 
		(let [chute (le-letra!)]
			(if (acertou? chute palavra)
			(do 
				(println "Acertou a letra!")
				(recur vidas palavra (conj acertos chute)))
			(do
				(println "Ixi, errou a letra!")
				(recur (dec vidas) palavra acertos))
			)
		)))

;; comecamos o jogo com o total de vidas, a palavra secreta, e nada de acertos
;; no repl
;; (require '[forca.core :as forca] :reload)
;; (forca/comeca-o-jogo) pra jogar
(defn comeca-o-jogo []
	(jogo total-de-vidas palavra-secreta #{}))

(defn -main [& args]
	(comeca-o-jogo))

