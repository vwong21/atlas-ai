import pandas as pd
from sentence_transformers import SentenceTransformer
from sklearn.ensemble import RandomForestClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, classification_report
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC


df = pd.read_csv('./data/cases.csv')

model = SentenceTransformer('sentence-transformers/all-MiniLM-L6-v2')
X = model.encode(df["text"].tolist(), show_progress_bar=True)
y = df["label"]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=3)

# knn = KNeighborsClassifier(n_neighbors=3)
# knn.fit(X_train, y_train)
# y_pred_knn = knn.predict(X_test)
# print("Accuracy:", accuracy_score(y_test, y_pred_knn))
# print(classification_report(y_test, y_pred_knn))

model = LogisticRegression(max_iter=1000)
model.fit(X_train, y_train)
y_pred = model.predict(X_test)
print("Accuracy:", accuracy_score(y_test, y_pred))
print(classification_report(y_test, y_pred))