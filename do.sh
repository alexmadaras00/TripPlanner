kubectl delete deployment postgres
kubectl delete persistentvolumeclaim postgres-pv-claim
kubectl delete persistentvolume postgres-pv-volume
kubectl delete secret postgres-secret
kubectl delete configmap postgres-config

kubectl delete service service-auth
kubectl delete deployment service-auth

kubectl delete service service-review
kubectl delete deployment service-review

kubectl delete service service-find
kubectl delete deployment service-find

kubectl delete service service-destination
kubectl delete deployment service-destination

kubectl delete service service-booking
kubectl delete deployment service-booking

kubectl delete service service-recommend
kubectl delete deployment service-recommend

kubectl delete service service-reserve
kubectl delete deployment service-reserve

kubectl delete service service-select
kubectl delete deployment service-select

kubectl apply -f db.yaml

docker build -t trip/service-auth ./service-auth
docker build -t trip/service-review ./service-review
docker build -t trip/service-find ./service-find-destination
docker build -t trip/service-destination ./service-destination-recommender
docker build -t trip/service-booking ./service-composition-booking
docker build -t trip/service-recommend ./service-composition-booking/service-recommend-accommodation
docker build -t trip/service-reserve ./service-composition-booking/service-reserve-accommodation
docker build -t trip/service-select ./service-composition-booking/service-select-accommodation

kubectl apply -f auth.yaml
kubectl apply -f reviews.yaml
kubectl apply -f booking.yaml
kubectl apply -f recommender.yaml
kubectl apply -f destination.yaml

