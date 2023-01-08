# dactylo

Hybride entre les logiciels d’entraînement dactylo classiques (comme sur les
sites web Monkeytype 1, 10fastfingers 2, Dactylotest 3, KeyBR 4.. ) et Tetris, 
notamment son mode multijoueur.
## Compilation et execution
Pour lancer le projet il faudra compiler le projet puis l'executer  
-Compilation : mvn compile  
-Execution : mvn clean javafx:run  
## Lancement des Tests
Voici les commandes pour le lancement des tests  
-mvn clean  
-mvn test
## Utilisation du programme
A l'éxecution du programme , on devra choisir tout d'abord le mode de jeu
auquel on voudra jouer, puis une fenêtre s'affichera avec les choix de paramètres
du jeu qui permet de choisir par exemple la limite de temps ou le nombre de
points de vie au départ. Après validation des paramètres , la fenêtre de jeu
s'ouvrira avec la liste des premiers mots à taper. On peux commencer à jouer quand 
on veut , le timer se lancera dès la première lettre écrite. Il ne faut pas contre
pas cliquer dans le rectangle du TextArea car cela va changer la position du curseur
et donc faire rater le programme. Il faut aussi éviter de faire ctrl+a qui pourrait 
aussi bloquer le programme.
## Fonctionnalités implementés
/*Décriver les fonctionnalités effectivement implémentées et
expliquant vos choix techniques originaux, le cas échéant*/
Nous avons implémenté les modes normal et jeu du Solo. Chaque mode a son propre choix
de paramètre avec une interface avant la partie. 
Les mots du programme sont une liste de mot dans un fichier qui sera lu et mettra chaque
mot dans une ArrayList de String , puis on prendra aléatoirement chaque mot de cette 
ArrayList qu'on mettra dans le String du texte à écrire qui va indiquer le texte au 
programme.
Le mode normal est implémenté avec un timer qui va indiquer la limite de temps de chaque
partie et va afficher les statistiques du joueur à la fin de la partie.
Si on veut valider un mot alors que celui ci n'est pas terminé, cela va terminer ce mot
avec le reste des caractères incorrectes qui va donc impacter les statistiques comme étant
pour chaque caractère incorrecte une erreur du joueur. 
Le mode jeu a les fonctionnalités de points de vie , les mots bonus , les niveaux qui
augmente quand le nombre nécessaire de mots est atteint par un compteur qui est décrémenté 
quand un mot correcte est validé.
Les mots bonus sont indiqués par leur couleur bleu changés si un tirage aléatoire ,qui va 
de 0 à 99, tire un nombre inférieur à 10 ce qui fait 10% de chance. Ensuite à chaque fois que
le joueur va taper une touche , on va vérifier si la couleur du mot est bleu , si oui alors on
mets les deux variables boolean 'bonus' et 'bonusActif' à true. Le premier boolean permet 
d'indiquer qu'on est toujours dans le mot bonus et donc qu'il faut continuer de modifier la 
seconde variable si besoin car elle s'occupe de vérifier à chaque lettre écrite si elle 
correspond , et sinon se met a false pour indiquer que le joueur n'a pas réussi a écrire le
mot bonus sans faute du premier coup donc sans compter les retour arrières et donc ne pas 
soigner le joueur. 