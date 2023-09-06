	/**
	 *
	 * @info Ce code JavaScript permet la génération de code en Java pour la classe
	 *       "MasterKey" présente dans le package "fr.luzi.defense". Étant donné que
	 *       les classes java ne permettent pas de contenir plus de 2^16 octets de
	 *       données, j'ai du procéder ainsi en écrivant ce générateur qui permet de
	 *       subdiviser une classe en y incluant d'autres classes. Le générateur
	 *       termine en écrivant la méthode qui va permettre d'assembler les clés
	 *       subdivisées afin de n'en produire qu'une seule via un appel de cette
	 *       méthode. Le problème posé par la limite en octets de classe est ainsi
	 *       contourné et l'application peut disposer d'une clé de chiffrement de
	 *       2^16 octes, soit 524 288 bits. La clé ainsi obtenue est employée par
	 *       l'algorithme de chiffrement par flot présent dans la classe
	 *       "fr.luzi.defense.FileCipher".
	 *
	 * @important S'utilise avec Node.js.
	 * @usage node javaFile.js typeByte nbKey
	 * @typeByte Le type de bytes souhaité. 4 types sont disponibles :
	 *		
	 * 		1) string
	 * 		2) int
	 * 		3) byte
	 *		4) bin
	 *
	 * @nbKey Le nombre (multiple) de clés souhaité. Sachant que chaque clé a une 
	 * longueur de 1024 octes.
	 *
	 * @language JavaScript.
	 * @author Randy LUZI
	 * @mail randy.luzi@gmail.com
	 * @license MIT
	 *
	 */

	// --- Import.
	const fs = require('fs');

	// --- Génération du code.
	let nameFile = "MasterKeyForJava.txt";
	var codeJava = "";
	let typeByte;
	let nbKey;
	
	// --- Vérification du premier argument programme.
	if(typeof process.argv[2] == "undefined"){
		
		typeByte = "String";
		
	}
	
	// --- Vérification du deuxième argument programme.
	if(typeof process.argv[3] == "undefined"){
		
		nbKey = 2**6;
		
	}else{
		
		nbKey = parseInt(process.argv[3]) || 2**6;
	}

	// --- Assignation du type de bytes souhaités.
	switch(typeByte){
		
		default :
		
		case "string":
			typeByte = "String";
			break;
			
		case "int":
			typeByte = "int";
			break;
			
		case "byte":
			typeByte = "byte";
			break;
			
		case "bin":
			typeByte = "bin";
			break;
	}
	
	// --- Génération du code.
	for(i=0; i<nbKey; i++){
		
		codeJava += `protected static class K${i} {\n\n`;
		codeJava += `	protected final static ${typeByte}[] K = {`;

		for(j=0; j<2**10; j++){
			
			switch(typeByte){
				
				default:
				
				case "String":
					codeJava += `"`+("0"+(Math.floor(Math.random()*2**8).toString(2**4))).substr(-2)+`"`;
					break;
				
				case "byte":
					codeJava += `0x`+("0"+(Math.floor(Math.random()*2**8).toString(2**4))).substr(-2);
					break;
				
				case "int":
					codeJava += (Math.floor(Math.random()*2**8));
					break;
				
				case "bin":
					codeJava += ("00000000"+(Math.floor(Math.random()*2**8).toString(2))).substr(-8);
					break;
				
			}
			
			if(j<2**2**4-1){ codeJava+=`, `; }
		}
		
		codeJava += `};\n\n	}\n\n`;
	}

	// --- Écriture de la méthode d'assemblage.
	codeJava += `protected static ${typeByte}[] getMasterKey() {\n\n`;

	codeJava += `	List<${typeByte}> masterKeyList = new ArrayList<>();\n\n`;

	for(i=0; i<nbKey; i++){

		codeJava += `	masterKeyList.addAll(Arrays.asList(K${i}.K));\n`;

	}
	
	codeJava += `\n\n	String[] MasterKey = masterKeyList.toArray(new ${typeByte}[masterKeyList.size()]);\n\n`; 
	codeJava += `	return MasterKey;\n\n`; 
	codeJava += `}\n\n`;

	// --- Écriture du code dans un fichier.
	fs.writeFileSync(nameFile, codeJava, err => {if (err) {console.error(err)}});
		
	// --- Affichage du résultat dans la console Node.js.
	console.log(codeJava+`\n`);
	console.log(`Fin du générateur !`);
	console.log(`Localisation du fichier -> ${nameFile}`);
	
	// --- Fin du générateur.