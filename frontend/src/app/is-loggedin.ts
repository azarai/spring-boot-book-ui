export function isLoggedin() {
  return !!localStorage.getItem('currentUser');
}