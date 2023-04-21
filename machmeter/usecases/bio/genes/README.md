### Bio Gene Protein Template

The usecase for which the database is modelled is for a ML application which will predict the interaction classification between two genes or between two protiens.

The load and preformance profiles simulate the typical mutations and queries that such an application would produce.

We have taken the schema by looking at some reserach publications in the field, for example:

https://pages.cs.wisc.edu/~dpage/kddcup2001/
https://www.researchgate.net/figure/Database-schema-Each-protein-has-a-PDB-ID-chain-ID-UniProt-AC-protein-name-gene_fig7_325014412

Schema:

We have the Gene table that depcits the gene information of an organims.
There is Protein table that is child table of gene, since a given gene can have multiple protiens.
The Classification table captures the localization of a given gene. Localization is roughly the location of the gene in the cell. The Interactions table is child table of Classification that captures for a given gene , how it correlates to a different gene, when the two genes interact.

Data load template:

We have a very unique use case here that the Interactions table captures the co-relation between two genes. Meaning we need two existing records from the same Gene table inorder to create one record of the Interaction table.

Hence there are two gene inserts here.
Since the Protien table is intervleaved in Gene, there are two protien tables inserts as well.

Similarly since the Classification table is recorded per Gene , there are 2 inserts for the classification as well.


Lastly we have taken the csv where we select some gene records which will later be used when running the perf test


Performance template:

We are simulating use cases of Protein systhesis - i.e new protien formations that are syntesied in a lab

Second use case is to query all interactions of one gene with others - this models a query of parent child interleaved tables

Third use case is update of gene attributes as and when the experiment result demand such

Lastly we have an aggregation use case to count how many genes have the same localization function.

