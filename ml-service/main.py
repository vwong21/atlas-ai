from joblib import load
from pathlib import Path
from sentence_transformers import SentenceTransformer
from fastapi import FastAPI
from pydantic import BaseModel

class ClassifyReq(BaseModel):
    prompt: str

MODEL_PATH = Path(__file__).parent / "models" / "classifier.joblib"
model = load(MODEL_PATH)

app = FastAPI()

emb_model = SentenceTransformer('sentence-transformers/all-MiniLM-L6-v2')

print("Modules Loaded")

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.post("/classify/")
def classify(req: ClassifyReq):
    print("received")
    print("Embedding Message")
    X = emb_model.encode([req.prompt])
    print("Predicting Outcome...")
    print(model.predict(X)[0])
    return{"class": model.predict(X)[0]}
