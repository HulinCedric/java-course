#include "CompteBon.h"

bool gg=false;
//La fameuse fonction résoudre, elle marche par récursivité
Pile* CompteBon::resoudre(){
	if(this->nok()) throw -2.0;
	Pile P(nbOperationsP(this->m_tirage.sommet()));
	Operation** pOP=new Operation*[this->m_tirage.sommet()];
	Pile *pRes=NULL;
	CompteBon* pCB=new CompteBon[nbOperationsP(this->m_tirage.sommet())];
	int x=0;
	//Addition
	for(int j=1; j<this->m_tirage.sommet(); j++){
		for(int k=j+1; k<=this->m_tirage.sommet(); k++){
			if(j!=k){
				pOP[x]=new Operation(this->m_tirage[j], '+', this->m_tirage[k]);
				//On empile l'addition
				P.empiler(pOP[x]);
				//Si le résultat est trouvé
				if(pOP[x]->resultat()==this->m_resultat){
					this->m_solution.empiler(pOP[x]);
					gg=true;
					return this->Solution();
				}
				//Sinon
				if(this->m_tirage.sommet()-1>=2){
					pCB=new CompteBon(this->m_resultat, this->m_tirage.sommet()-1);
					pCB->m_tirage=this->m_tirage;
					pCB->m_tirage-(this->m_tirage[j]);
					pCB->m_tirage-(this->m_tirage[k]);
					pCB->m_tirage.empiler(pOP[x]->resultat());
					pRes=pCB->resoudre();
				}
				if(gg){
					this->m_solution=*pRes;
					this->m_solution.empiler(pOP[x]);
					return this->Solution();
				}
				x++;
			}		
		}
	}
	//Multiplication
	for(int j=1; j<=this->m_tirage.sommet(); j++){
		for(int k=j+1; k<=this->m_tirage.sommet(); k++){
			if(this->m_tirage[j]!=1 && this->m_tirage[k]!=1 && j!=k){
				pOP[x]=new Operation(this->m_tirage[j], '*', this->m_tirage[k]);
				P.empiler(pOP[x]);
				//Si le résultat est trouvé
				if(pOP[x]->resultat()==this->m_resultat){
					this->m_solution.empiler(pOP[x]);
					gg=true;
					return this->Solution();
				}
				//Sinon
				if(this->m_tirage.sommet()-1>=2){
					pCB=new CompteBon(this->m_resultat, this->m_tirage.sommet()-1);
					pCB->m_tirage=this->m_tirage;
					pCB->m_tirage-(this->m_tirage[j]);
					pCB->m_tirage-(this->m_tirage[k]);
					pCB->m_tirage.empiler(pOP[x]->resultat());
					pRes=pCB->resoudre();
				}
				if(gg){
					this->m_solution=*pRes;
					this->m_solution.empiler(pOP[x]);
					return this->Solution();
				}
				x++;
			}
		}
	}
	//Soustraction
	for(int j=1; j<=this->m_tirage.sommet(); j++){
		for(int k=1; k<=this->m_tirage.sommet(); k++){
			if(this->m_tirage[j]-this->m_tirage[k]>0 && j!=k){
				pOP[x]=new Operation(this->m_tirage[j], '-', this->m_tirage[k]);			
				P.empiler(pOP[x]);
				//Si le résultat est trouvé
				if(pOP[x]->resultat()==this->m_resultat){
					this->m_solution.empiler(pOP[x]);
					gg=true;
					return this->Solution();
				}
				//Sinon
				if(this->m_tirage.sommet()-1>=2){
					pCB=new CompteBon(this->m_resultat, this->m_tirage.sommet()-1);
					pCB->m_tirage=this->m_tirage;
					pCB->m_tirage-(this->m_tirage[j]);
					pCB->m_tirage-(this->m_tirage[k]);
					pCB->m_tirage.empiler(pOP[x]->resultat());
					pRes=pCB->resoudre();
				}
				if(gg){
					this->m_solution=*pRes;
					this->m_solution.empiler(pOP[x]);
					return this->Solution();
				}
				x++;
			}
		}
	}
	//Division
	for(int j=1; j<=this->m_tirage.sommet(); j++){
		for(int k=1; k<=this->m_tirage.sommet(); k++){
			if(this->m_tirage[k]!=0 && this->m_tirage[j]%this->m_tirage[k]==0 && j!=k){
				pOP[x]=new Operation(this->m_tirage[j], '/', this->m_tirage[k]);			
				P.empiler(pOP[x]);
				//Si le résultat est trouvé
				if(pOP[x]->resultat()==this->m_resultat){
					this->m_solution.empiler(pOP[x]);
					gg=true;
					return this->Solution();
				}
				//Sinon
				if(this->m_tirage.sommet()-1>=2){
					pCB=new CompteBon(this->m_resultat, this->m_tirage.sommet()-1);
					pCB->m_tirage=this->m_tirage;
					pCB->m_tirage-(this->m_tirage[j]);
					pCB->m_tirage-(this->m_tirage[k]);
					pCB->m_tirage.empiler(pOP[x]->resultat());
					pRes=pCB->resoudre();
				}
				if(gg){
					this->m_solution=*pRes;
					this->m_solution.empiler(pOP[x]);
					return this->Solution();
				}
				x++;
			}
		}
	}
	/*if(pOP!=NULL){
		for(int z=0; z<this->m_tirage.sommet(); z++){
			if(pOP[z]!=NULL) delete pOP[z];
		}
		delete [] pOP;
	}
	if(pCB!=NULL) delete [] pCB;*/
	return pRes;
}
